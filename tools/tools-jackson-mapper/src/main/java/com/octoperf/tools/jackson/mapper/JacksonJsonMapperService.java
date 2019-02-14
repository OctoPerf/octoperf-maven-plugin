package com.octoperf.tools.jackson.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

final class JacksonJsonMapperService implements JsonMapperService {

  private final ObjectMapper mapper;

  JacksonJsonMapperService(final ObjectMapper mapper, final Set<JsonRegistrator> registrators) {
    super();
    this.mapper = checkNotNull(mapper);
    for (final JsonRegistrator registrator : registrators) {
      registrator
        .getMappings()
        .entrySet()
        .stream()
        .map(e -> new NamedType(e.getKey(), e.getValue()))
        .forEach(mapper::registerSubtypes);
    }
  }

  @Override
  public String toJson(final Object instance) throws IOException {
    return mapper.writeValueAsString(instance);
  }

  @Override
  public String toPrettyJson(final Object instance) throws IOException {
    return mapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(instance);
  }

  @Override
  public <T> String toJson(final T instance, final TypeReference<T> type) throws IOException {
    return mapper.writerFor(type).writeValueAsString(instance);
  }

  @Override
  public <T> T fromJson(final String json, final Class<T> clazz) throws IOException {
    return mapper.readValue(json, clazz);
  }

  @Override
  public <T> T fromJson(final String json, final Type type) throws IOException {
    return mapper.readValue(json, new SimpleTypeReference(type));
  }

  @Override
  public <T> T fromJson(String json, TypeReference<T> type) throws IOException {
    return mapper.readValue(json, type);
  }
}
