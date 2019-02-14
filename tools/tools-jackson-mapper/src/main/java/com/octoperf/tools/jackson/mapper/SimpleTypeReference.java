package com.octoperf.tools.jackson.mapper;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

import static com.google.common.base.Preconditions.checkNotNull;

final class SimpleTypeReference extends TypeReference<Object> {
  private final Type type;

  SimpleTypeReference(final Type type) {
    super();
    this.type = checkNotNull(type);
  }

  @Override
  public Type getType() {
    return type;
  }
}