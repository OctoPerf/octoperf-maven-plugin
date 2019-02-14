package com.octoperf.commons.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
public final class ValueWrapper<T> {
 
  T value;
  
  @JsonCreator
  public ValueWrapper(
      @JsonProperty("value") final T value) {
    super();
    this.value = checkNotNull(value);
  }
  
}
