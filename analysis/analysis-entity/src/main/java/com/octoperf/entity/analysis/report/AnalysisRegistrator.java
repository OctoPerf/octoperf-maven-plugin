package com.octoperf.entity.analysis.report;

import com.octoperf.tools.jackson.mapper.JsonRegistrator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class AnalysisRegistrator implements JsonRegistrator {

  @Override
  public Map<Class<?>, String> getMappings() {
    return Map.of(
      ExportReportConfig.class, "ExportReportConfig"
    );
  }
}
