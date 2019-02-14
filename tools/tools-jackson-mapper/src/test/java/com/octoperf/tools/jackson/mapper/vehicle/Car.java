package com.octoperf.tools.jackson.mapper.vehicle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class Car implements Vehicle {
  String name;

  @JsonCreator
  public Car(@JsonProperty("name") final String name) {
    super();
    this.name = requireNonNull(name);
  }
}
