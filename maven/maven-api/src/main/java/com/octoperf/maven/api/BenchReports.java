package com.octoperf.maven.api;

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
    String projectId,
    String reportId);
}
