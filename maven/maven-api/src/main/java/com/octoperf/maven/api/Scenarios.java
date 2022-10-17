package com.octoperf.maven.api;

import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.runtime.Scenario;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface Scenarios {

  void removeAll(String projectId);

  Scenario createFromFile(
    String workspaceId,
    String projectId,
    File jsonFile) throws IOException;

  Scenario findByName(String projectId, String name) throws IOException;

  BenchReport startTest(String scenarioId,
                        Optional<String> templateId,
                        Optional<String> testName) throws IOException;

  void log(Scenario scenario);
}
