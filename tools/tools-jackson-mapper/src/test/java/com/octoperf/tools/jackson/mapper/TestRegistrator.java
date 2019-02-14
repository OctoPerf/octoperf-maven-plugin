package com.octoperf.tools.jackson.mapper;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class TestRegistrator implements JsonRegistrator {

  public Map<Class<?>, String> getMappings() {
    return ImmutableMap.of();
  }
}
