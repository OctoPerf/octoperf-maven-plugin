package com.octoperf.maven.rest;

import com.octoperf.analysis.task.entity.PrintReportTask;
import com.octoperf.analysis.task.entity.PrintReportTaskResult;
import com.octoperf.entity.analysis.report.ExportReportConfig;
import com.octoperf.maven.api.Tasks;
import com.octoperf.task.entity.TaskResult;
import com.octoperf.task.rest.api.TasksApi;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestTasks implements Tasks {
  private static final String COVER_PAGE_VALUE = "# $COMPANY_NAME$\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<h2 style='text-align: right;'>$REPORT_NAME$</h2>\n" +
    "<h3 style='text-align: right;'>$REPORT_DATE$<br></h3>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>";

  private static final Map<String, Integer> TABLES_ROW_COUNT = Map.of(
    "StatisticTableReportItem", 20,
    "StatisticTreeReportItem", 20,
    "ErrorsReportItem", 20,
    "ThresholdAlarmReportItem", 20,
    "TextualMonitorReportItem", 5,
    "SynopsisReportItem", 6
  );
  private static final int MAX_RETRIES = 60;
  private static final long SLEEP_MILLIS = 5000L;

  @NonNull
  TasksApi api;
  @NonNull
  CallService calls;

  @Override
  public void generatePdfReport(final String reportId) throws InterruptedException, IOException {
    final ExportReportConfig config = ExportReportConfig
      .builder()
      .orientation("landscape")
      .coverPage(COVER_PAGE_VALUE)
      .tablesRowCount(TABLES_ROW_COUNT)
      .build();

    final PrintReportTask task = new PrintReportTask(reportId, config);

    log.info("Generating PDF Report...");
    final String taskId = calls.execute(api.submit(task))
      .orElseThrow()
      .string();

    Optional<TaskResult> result;

    int i = 0;
    do {
      i++;

      Thread.sleep(SLEEP_MILLIS);

      result = calls.execute(api.getResult(taskId));

    } while (result.isEmpty() && i < MAX_RETRIES);
  }
}
