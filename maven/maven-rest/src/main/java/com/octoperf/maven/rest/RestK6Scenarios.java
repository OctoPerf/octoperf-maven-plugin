package com.octoperf.maven.rest;

import com.octoperf.entity.runtime.k6.K6ScenarioConversion;
import com.octoperf.entity.runtime.k6.K6ScenarioRequest;
import com.octoperf.maven.api.K6Scenarios;
import com.octoperf.runtime.rest.api.K6ScenarioApi;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestK6Scenarios implements K6Scenarios {
  @NonNull
  K6ScenarioApi api;
  @NonNull
  CallService calls;

  @Override
  public K6ScenarioConversion create(final String projectId, final K6ScenarioRequest request) throws IOException {
    return calls.executeOrThrow(api.create(projectId, request));
  }
}
