package com.octoperf.entity.runtime;




public enum BenchResultState {
  CREATED,
  PENDING,
  SCALING,
  PREPARING,
  INITIALIZING,
  RUNNING,
  FINISHED,
  ABORTED,
  ERROR
}
