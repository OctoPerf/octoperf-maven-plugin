package com.octoperf.maven.api;

import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.runtime.Scenario;

import java.io.File;
import java.io.IOException;

public interface Scenarios {

  void removeAll(String projectId);

  Scenario createFromFile(
    String workspaceId,
    String projectId,
    File jsonFile) throws IOException;

  BenchReport startTest(String scenarioId) throws IOException;

  void log(Scenario scenario);
}
