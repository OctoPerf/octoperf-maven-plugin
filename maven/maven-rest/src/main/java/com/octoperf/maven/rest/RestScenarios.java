package com.octoperf.maven.rest;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Closer;
import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.runtime.Scenario;
import com.octoperf.maven.api.Scenarios;
import com.octoperf.runtime.rest.api.ScenarioApi;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Charsets.UTF_8;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.StandardOpenOption.READ;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestScenarios implements Scenarios {
  @NonNull
  JsonMapperService mapper;
  @NonNull
  ScenarioApi api;
  @NonNull
  CallService calls;

  @Override
  public void removeAll(final String projectId) {
    calls
      .execute(api.list(projectId))
      .orElse(ImmutableList.of())
      .stream()
      .map(Scenario::getId)
      .map(api::delete)
      .forEach(calls::execute);
  }

  @Override
  public Scenario createFromFile(
    String workspaceId,
    String projectId,
    final File jsonFile) throws IOException {
    final String json = readFile(jsonFile);
    final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    return calls
      .execute(api.newScenario(workspaceId, projectId, body))
      .orElseThrow(() -> new IOException("Could not create scenario from JSON"));
  }

  private String readFile(final File file) throws IOException {
    final Closer closer = Closer.create();
    try {
      final InputStream input = closer.register(newInputStream(file.toPath(), READ));
      return IOUtils.toString(input, UTF_8);
    } finally {
      closer.close();
    }
  }

  @Override
  public BenchReport startTest(final String scenarioId) throws IOException {
    return calls
      .execute(api.run(scenarioId))
      .orElseThrow(() -> new IOException("Could not start scenarioId=" + scenarioId));
  }

  @Override
  public void log(final Scenario scenario) {
    log.info("Scenario:");
    log.info("- id: " + scenario.getId() + ",");
    log.info("- name: " + scenario.getName() + ",");
    if (!scenario.getDescription().isEmpty()) {
      log.info("- description: " + scenario.getDescription());
    }
  }

}
