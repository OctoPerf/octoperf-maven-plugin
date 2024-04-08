package com.octoperf.maven.rest;

import com.octoperf.maven.api.BenchReports;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.removeEnd;

@Slf4j
@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestBenchReports implements BenchReports {
  private static final String REPORT_URL = "/ui/workspace/%s/project/%s/analysis/report/%s";

  @Override
  public String getReportUrl(
    final String serverUrl,
    final String workspaceId,
    final String projectId,
    final String reportId) {
    final String baseUrl = removeEnd(serverUrl, "/");
    return format(baseUrl + REPORT_URL, workspaceId, projectId, reportId);
  }
}
