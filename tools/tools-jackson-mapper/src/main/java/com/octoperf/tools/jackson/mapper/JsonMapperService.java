package com.octoperf.tools.jackson.mapper;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.lang.reflect.Type;

public interface JsonMapperService {

  String toJson(Object instance) throws IOException;

  String toPrettyJson(Object instance) throws IOException;

  <T> String toJson(T instance, TypeReference<T> type) throws IOException;

  <T> T fromJson(String json, Class<T> clazz) throws IOException;

  <T> T fromJson(String json, Type type) throws IOException;
  
  <T> T fromJson(String json, TypeReference<T> type) throws IOException;
}
