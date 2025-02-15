package com.octoperf.maven.plugin;

import com.google.common.collect.ImmutableSet;
import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.analysis.report.BenchReportTemplate;
import com.octoperf.entity.analysis.report.graph.MetricValues;
import com.octoperf.entity.runtime.BenchResult;
import com.octoperf.entity.runtime.BenchResultState;
import com.octoperf.entity.runtime.Scenario;
import com.octoperf.maven.api.*;
import com.octoperf.maven.plugin.threshold.ThresholdAlarms;
import com.octoperf.maven.plugin.threshold.ThresholdSeverity;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.octoperf.entity.runtime.BenchResultState.*;
import static java.lang.Thread.sleep;
import static java.util.Optional.ofNullable;
import static org.joda.time.DateTime.now;
import static org.joda.time.format.DateTimeFormat.forPattern;

@Slf4j
@Mojo(name = "execute-scenario")
public class ExecuteScenario extends AbstractOctoPerfMojo {
  private static final long TEN_SECS = 10_000L;
  private static final DateTimeFormatter DATE_FORMAT = forPattern("HH:mm:ss");
  private static final Set<BenchResultState> TERMINAL_STATES = ImmutableSet.of(
    ABORTED,
    FINISHED,
    ERROR
  );

  @Parameter(defaultValue = "Scenario")
  protected String scenarioName = "";
  @Parameter(defaultValue = "true")
  protected Boolean isDownloadJUnitReports = true;
  @Parameter(defaultValue = "true")
  protected Boolean isDownloadLogs = true;
  @Parameter(defaultValue = "false")
  protected Boolean isDownloadJTLs = false;
  @Parameter(defaultValue = "false")
  protected Boolean isGeneratePdfReport = false;
  @Parameter
  protected ThresholdSeverity stopTestIfThreshold = null;
  @Parameter
  protected String reportTemplateName = null;
  @Parameter
  protected String testName = "";
  @Parameter
  protected Map<String, String> properties = new HashMap<>();

  @Override
  public void execute() throws MojoExecutionException {
    final GenericApplicationContext context = newContext();

    final Log log = getLog();
    final BenchResults results = context.getBean(BenchResults.class);
    try {
      final Workspaces workspaces = context.getBean(Workspaces.class);
      log.info("Workspace: " + workspaceName);
      final String workspaceId = workspaces.getWorkspaceId(workspaceName);

      final Projects projects = context.getBean(Projects.class);
      log.info("Project: " + projectName);
      final String projectId = projects.getProjectId(workspaceId, projectName);

      final Scenarios scenarios = context.getBean(Scenarios.class);
      final Scenario scenario = scenarios.findByName(projectId, scenarioName);
      scenarios.log(scenario);

      final BenchReportTemplates templates = context.getBean(BenchReportTemplates.class);
      final Optional<String> templateId = ofNullable(reportTemplateName)
        .flatMap(name -> templates.find(workspaceId, name))
        .map(BenchReportTemplate::getId);
      templateId.ifPresent(id -> log.info("Report Template: " + reportTemplateName));

      runTest(
        scenarios,
        results,
        context.getBean(BenchReports.class),
        context.getBean(BenchMetrics.class),
        context.getBean(JunitService.class),
        context.getBean(BenchLogs.class),
        context.getBean(ThresholdAlarms.class),
        context.getBean(Tasks.class),
        scenario.getId(),
        workspaceId,
        templateId
      );
    } catch (final IOException | InterruptedException e) {
      log.error(e);
      throw new MojoExecutionException("", e);
    }
  }


  private void runTest(
    final Scenarios scenarios,
    final BenchResults results,
    final BenchReports reports,
    final BenchMetrics metrics,
    final JunitService junits,
    final BenchLogs logs,
    final ThresholdAlarms alarms,
    final Tasks tasks,
    final String scenarioId,
    final String workspaceId,
    final Optional<String> templateId) throws IOException, InterruptedException {
    final BenchReport report = scenarios.startTest(
      scenarioId,
      templateId,
      ofNullable(testName),
      properties
    );

    BenchResult benchResult = null;
    try {
      benchResult = results.find(report.getBenchResultIds().get(0));

      final String reportUrl = reports.getReportUrl(
        serverUrl,
        workspaceId,
        report.getProjectId(),
        report.getId()
      );
      final String benchResultId = benchResult.getId();
      log.info("Bench Report: " + reportUrl);

      DateTime startTime = null;
      while (true) {
        sleep(TEN_SECS);

        benchResult = results.find(benchResultId);
        final BenchResultState currentState = benchResult.getState();

        if (stopTestIfThreshold != null && alarms.hasAlarms(benchResultId, stopTestIfThreshold)) {
          throw new IOException("Threshold with severity >= " + stopTestIfThreshold + " encountered! Aborting test...");
        }

        if (currentState == RUNNING) {
          final DateTime now = now();
          startTime = firstNonNull(startTime, now);

          final MetricValues values = metrics.getMetrics(benchResultId);
          final String printable = metrics.toPrintable(startTime, values);
          final String nowStr = DATE_FORMAT.print(now);

          final String progress = String.format("[%.2f%%] ", results.getProgress(benchResultId));
          log.info(progress + nowStr + " - " + printable);
        } else if (TERMINAL_STATES.contains(currentState)) {
          buildDir.mkdirs();

          if (isDownloadJUnitReports) {
            junits.saveJUnitReport(buildDir, benchResultId);
          }

          if (isDownloadLogs) {
            logs.downloadLogFiles(buildDir, benchResultId);
          }

          if (isDownloadJTLs) {
            logs.downloadJtlFiles(buildDir, benchResultId);
          }

          if (isGeneratePdfReport) {
            tasks.generatePdfReport(report.getId());
            logs.downloadPdfFiles(buildDir, benchResultId);
          }

          benchResult = null;
          log.info("Test finished with state: " + currentState);
          break;
        } else {
          log.info("Preparing test.. (" + currentState + ")");
        }
      }
    } finally {
      ofNullable(benchResult)
        .map(BenchResult::getId)
        .ifPresent(results::stopTest);
    }
  }
}
