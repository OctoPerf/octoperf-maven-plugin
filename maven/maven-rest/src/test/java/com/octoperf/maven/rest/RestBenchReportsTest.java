package com.octoperf.maven.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestBenchReportsTest {

  @Test
  void shouldCreateBenchUrl() {
    final RestBenchReports service = new RestBenchReports();
    final String reportUrl = service.getReportUrl("https://api.octoperf.com", "workspaceId", "projectId", "reportId");
    assertEquals(
      "https://api.octoperf.com/ui/workspace/workspaceId/project/projectId/analysis/report/reportId",
      reportUrl
    );
  }
}