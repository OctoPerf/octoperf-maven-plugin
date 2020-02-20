package com.octoperf.maven.rest;

import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.runtime.BenchResult;
import com.octoperf.maven.api.BenchReports;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.removeEnd;

@Slf4j
@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestBenchReports implements BenchReports {
  private static final String REPORT_URL = "/#/app/workspace/%s/project/%s/analysis/%s";

  @Override
  public String getReportUrl(
    final String serverUrl,
    final String workspaceId,
    final BenchReport report) {
    final String baseUrl = removeEnd(serverUrl, "/") + "/app";
    return String.format(
      baseUrl + REPORT_URL,
      workspaceId,
      report.getProjectId(),
      report.getId()
    );
  }
}
