package com.octoperf.maven.plugin;

import com.google.common.collect.ImmutableSet;
import com.octoperf.entity.analysis.report.BenchReport;
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
import java.util.Set;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.octoperf.entity.runtime.BenchResultState.*;
import static com.octoperf.maven.plugin.threshold.ThresholdSeverity.PASSED;
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
  @Parameter
  protected ThresholdSeverity stopTestIfThreshold = PASSED;

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

      runTest(
        scenarios,
        results,
        context.getBean(BenchReports.class),
        context.getBean(BenchMetrics.class),
        context.getBean(JunitService.class),
        context.getBean(BenchLogs.class),
        context.getBean(ThresholdAlarms.class),
        scenario.getId(),
        workspaceId
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
    final String scenarioId,
    final String workspaceId) throws IOException, InterruptedException {
    final BenchReport benchReport = scenarios.startTest(scenarioId);

    BenchResult benchResult = null;
    try {
      benchResult = results.find(benchReport.getBenchResultIds().get(0));

      final String reportUrl = reports.getReportUrl(
        serverUrl,
        workspaceId,
        benchReport
      );
      log.info("Bench Report: " + reportUrl);

      DateTime startTime = null;
      while (true) {
        Thread.sleep(TEN_SECS);

        benchResult = results.find(benchResult.getId());
        final BenchResultState currentState = benchResult.getState();

        if (alarms.hasAlarms(benchResult.getId(), stopTestIfThreshold)) {
          throw new IOException("Threshold with severity=" + stopTestIfThreshold + " encountered! Aborting test...");
        }

        if (currentState == RUNNING) {
          final DateTime now = now();
          startTime = firstNonNull(startTime, now);

          final MetricValues values = metrics.getMetrics(benchResult.getId());
          final String printable = metrics.toPrintable(startTime, values);
          final String nowStr = DATE_FORMAT.print(now);

          final String progress = String.format("[%.2f%%] ", results.getProgress(benchResult.getId()));
          log.info(progress + nowStr + " - " + printable);
        } else if (TERMINAL_STATES.contains(currentState)) {
          buildDir.mkdirs();

          if (isDownloadJUnitReports) {
            junits.saveJUnitReport(buildDir, benchResult.getId());
          }

          if (isDownloadLogs) {
            logs.downloadLogFiles(buildDir, benchResult.getId());
          }

          if (isDownloadJTLs) {
            logs.downloadJtlFiles(buildDir, benchResult.getId());
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
