package com.octoperf.maven.plugin;

import com.google.common.collect.ImmutableSet;
import com.octoperf.Application;
import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.analysis.report.graph.MetricValues;
import com.octoperf.entity.runtime.BenchResult;
import com.octoperf.entity.runtime.BenchResultState;
import com.octoperf.entity.runtime.Scenario;
import com.octoperf.maven.api.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.octoperf.entity.runtime.BenchResultState.*;
import static java.util.Optional.ofNullable;
import static org.joda.time.DateTime.now;
import static org.joda.time.format.DateTimeFormat.forPattern;

@Slf4j
@Mojo(name = "run")
public class RunJmx extends AbstractMojo {
  private static final long TEN_SECS = 10_000L;
  private static final DateTimeFormatter DATE_FORMAT = forPattern("HH:mm:ss");
  private static final Set<BenchResultState> TERMINAL_STATES = ImmutableSet.of(
    ABORTED,
    FINISHED,
    ERROR
  );

  @Parameter(defaultValue = "${project.build.directory}", readonly = true)
  private File buildDir;
  @Parameter(defaultValue = "${project.basedir}/script.jmx")
  private File jmxFile;
  @Parameter(defaultValue = "https://api.octoperf.com")
  private String serverUrl = "";
  @Parameter(required = true)
  private String apiKey = "";
  @Parameter(defaultValue = "Default")
  private String workspaceName = "";
  @Parameter(required = true)
  private String projectName = "";
  @Parameter(defaultValue = "${project.basedir}/src/main/resources")
  private File resourcesFolder;
  @Parameter(defaultValue = "${project.basedir}/scenario.json")
  private File scenarioFile;
  @Parameter(defaultValue = "true")
  private Boolean isDownloadJUnitReports = true;
  @Parameter(defaultValue = "true")
  private Boolean isDownloadLogs = true;
  @Parameter(defaultValue = "false")
  private Boolean isDownloadJTLs = false;

  @Override
  public void execute() throws MojoExecutionException {
    StaticLoggerBinder.getSingleton().setMavenLog(getLog());

    System.setProperty("apiKey", apiKey);
    System.setProperty("serverUrl", serverUrl);
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.setEnvironment(new StandardEnvironment());
    context.register(Application.class);
    context.refresh();

    final BenchResults benchResults = context.getBean(BenchResults.class);

    BenchResult benchResult = null;

    final Log log = getLog();
    try {

      final Workspaces workspaces = context.getBean(Workspaces.class);
      final String workspaceId = workspaces
        .getWorkspaceId(workspaceName)
        .orElseThrow(() -> new IOException("Could not find workspace '" + workspaceName + "'"));

      final Projects projects = context.getBean(Projects.class);
      final String projectId = projects
        .getProjectId(workspaceId, projectName)
        .orElseThrow(() -> new IOException("Could not find project '" + projectName + "'"));

      final Scenarios scenarios = context.getBean(Scenarios.class);
      scenarios.removeAll(projectId);

      final VirtualUsers virtualUsers = context.getBean(VirtualUsers.class);
      virtualUsers.removeAll(projectId);

      final HttpServers servers = context.getBean(HttpServers.class);
      servers.removeAll(projectId);

      final ProjectFiles files = context.getBean(ProjectFiles.class);
      files.removeAll(projectId);
      files.uploadAll(projectId, resourcesFolder);

      virtualUsers.importJMX(projectId, jmxFile);
      final Scenario scenario = scenarios.createFromFile(
        workspaceId,
        projectId,
        scenarioFile
      );
      scenarios.log(scenario);

      final BenchReport benchReport = scenarios.startTest(scenario.getId());
      benchResult = benchResults.find(benchReport.getBenchResultIds().get(0));

      final BenchReports reports = context.getBean(BenchReports.class);
      final String reportUrl = reports.getReportUrl(
        serverUrl,
        workspaceId,
        benchResult.getResultProjectId(),
        benchReport
      );
      log.info("Bench Report: " + reportUrl);

      try {
        DateTime startTime = null;
        final BenchMetrics metrics = context.getBean(BenchMetrics.class);

        while(true) {
          Thread.sleep(TEN_SECS);

          benchResult = benchResults.find(benchResult.getId());
          final BenchResultState currentState = benchResult.getState();

          if (currentState == RUNNING) {
            final DateTime now = now();
            startTime = firstNonNull(startTime, now);

            final MetricValues values = metrics.getMetrics(benchResult.getId());
            final String printable = metrics.toPrintable(startTime, values);
            final String nowStr = DATE_FORMAT.print(now);

            final String progress = String.format("[%.2f%%] ", benchResults.getProgress(benchResult.getId()));
            log.info(progress + nowStr + " - " + printable);
          } else if(TERMINAL_STATES.contains(currentState)) {
            log.info("Test finished with state: " + currentState);
            break;
          } else {
            log.info("Preparing test.. (" + currentState +")");
          }
        }

        buildDir.mkdirs();
        if (isDownloadJUnitReports) {
          final JunitService junits = context.getBean(JunitService.class);
          junits.saveJUnitReport(buildDir, benchResult.getId());
        }

        final BenchLogs logs = context.getBean(BenchLogs.class);
        if (isDownloadLogs) {
          logs.downloadLogFiles(buildDir, benchResult.getId());
        }

        if (isDownloadJTLs) {
          logs.downloadJtlFiles(buildDir, benchResult.getId());
        }
      } finally {
        benchResult = null;
      }
    } catch (final IOException | InterruptedException e) {
      log.error(e);
      throw new MojoExecutionException("", e);
    } finally {
      ofNullable(benchResult)
        .map(BenchResult::getId)
        .ifPresent(benchResults::stopTest);
    }
  }
}
