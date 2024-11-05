package com.octoperf.entity.runtime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Value
public class PropertiesSettings {
  public static final PropertiesSettings EMPTY_PROPS = new PropertiesSettings(Map.of());

  Map<String, String> map;

  @JsonCreator
  public PropertiesSettings(@JsonProperty("map") final Map<String, String> map) {
    this.map = requireNonNull(map);
  }
}
