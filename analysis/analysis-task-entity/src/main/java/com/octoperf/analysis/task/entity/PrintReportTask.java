package com.octoperf.analysis.task.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.octoperf.entity.analysis.report.ExportReportConfig;
import com.octoperf.task.entity.Task;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class PrintReportTask implements Task {

  String benchReportId;
  ExportReportConfig config;

  @JsonCreator
  public PrintReportTask(@JsonProperty("benchReportId") final String benchReportId,
                         @JsonProperty("config") final ExportReportConfig config) {
    super();
    this.benchReportId = requireNonNull(benchReportId);
    this.config = requireNonNull(config);
  }
}
