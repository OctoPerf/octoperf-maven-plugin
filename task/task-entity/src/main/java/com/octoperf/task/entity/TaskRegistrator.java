package com.octoperf.task.entity;

import com.google.common.collect.ImmutableMap;
import com.octoperf.tools.jackson.mapper.JsonRegistrator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class TaskRegistrator implements JsonRegistrator {

  @Override
  public Map<Class<?>, String> getMappings() {
    final ImmutableMap.Builder<Class<?>, String> builder = ImmutableMap.builder();

    builder.put(FailedTaskResult.class, "FailedTaskResult");

    return builder.build();
  }
}
