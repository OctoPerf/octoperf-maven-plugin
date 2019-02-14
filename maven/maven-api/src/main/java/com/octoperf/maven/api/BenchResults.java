package com.octoperf.maven.api;

import com.octoperf.entity.runtime.BenchResult;

import java.io.IOException;

public interface BenchResults {

  BenchResult find(String id) throws IOException;

  void stopTest(String benchResultId);

  double getProgress(String id);
}
