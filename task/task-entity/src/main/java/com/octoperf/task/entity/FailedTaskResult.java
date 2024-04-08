package com.octoperf.task.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class FailedTaskResult implements TaskResult {

  String stackTrace;

  @JsonCreator
  public FailedTaskResult(
      @JsonProperty("stackTrace") final String stackTrace) {
    super();
    this.stackTrace = requireNonNull(stackTrace);
  }

}
