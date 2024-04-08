package com.octoperf.analysis.task.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.octoperf.task.entity.TaskResult;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class PrintReportTaskResult implements TaskResult {
  String filename;

  @JsonCreator
  public PrintReportTaskResult(@JsonProperty("filename") final String filename) {
    super();
    this.filename = requireNonNull(filename);
  }
}
