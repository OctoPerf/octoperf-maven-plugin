package com.octoperf.maven.api;

import com.octoperf.entity.analysis.report.BenchReportTemplate;

import java.util.Optional;

public interface BenchReportTemplates {

  Optional<BenchReportTemplate> find(String workspaceId, String name);
}
