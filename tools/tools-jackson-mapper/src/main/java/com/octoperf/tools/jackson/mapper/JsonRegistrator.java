package com.octoperf.tools.jackson.mapper;

import java.util.Map;

@FunctionalInterface
public interface JsonRegistrator {

  Map<Class<?>, String> getMappings();
}
