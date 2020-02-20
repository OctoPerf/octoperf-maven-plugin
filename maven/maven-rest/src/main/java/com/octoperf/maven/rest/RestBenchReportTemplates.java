package com.octoperf.maven.rest;

import com.google.common.collect.ImmutableList;
import com.octoperf.analysis.rest.client.BenchReportTemplatesApi;
import com.octoperf.entity.analysis.report.BenchReportTemplate;
import com.octoperf.maven.api.BenchReportTemplates;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestBenchReportTemplates implements BenchReportTemplates {
  @NonNull
  BenchReportTemplatesApi api;
  @NonNull
  CallService calls;

  @Override
  public Optional<BenchReportTemplate> find(
    final String workspaceId,
    final String name) {
    return calls
      .execute(api.find(workspaceId))
      .orElse(ImmutableList.of())
      .stream()
      .filter(t -> Objects.equals(name, t.getName()))
      .findFirst();
  }
}
