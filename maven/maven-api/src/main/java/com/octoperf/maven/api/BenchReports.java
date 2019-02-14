package com.octoperf.maven.api;

import com.octoperf.entity.analysis.report.BenchReport;

/**
 * Provides common reporting operations.
 *  
 * @author jerome
 *
 */
@FunctionalInterface
public interface BenchReports {

  String getReportUrl(
    final String serverUrl,
    String workspaceId,
    String resultProjectId,
    BenchReport report);
}
