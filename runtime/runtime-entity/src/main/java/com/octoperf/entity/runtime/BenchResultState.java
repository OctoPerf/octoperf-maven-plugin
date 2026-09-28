package com.octoperf.entity.runtime;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum BenchResultState {
  CREATED,
  PENDING,
  SCALING,
  PREPARING,
  INITIALIZING,
  RUNNING,
  FINISHED,
  ABORTED,
  ERROR,
  /** A state a newer server reports. */
  @JsonEnumDefaultValue
  UNKNOWN
}
