package com.octoperf.analysis.task.entity;

import com.google.common.collect.ImmutableMap;
import com.octoperf.tools.jackson.mapper.JsonRegistrator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class PrintReportTaskRegistrator implements JsonRegistrator {

  @Override
  public Map<Class<?>, String> getMappings() {
    final ImmutableMap.Builder<Class<?>, String> builder = ImmutableMap.builder();

    builder.put(PrintReportTask.class, "PrintReportTask");
    builder.put(PrintReportTaskResult.class, "PrintReportTaskResult");

    return builder.build();
  }
}
