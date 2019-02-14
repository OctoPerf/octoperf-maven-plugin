package com.octoperf.tools.jackson.mapper.vehicle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
public class Truck implements Vehicle {
  String name;

  @JsonCreator
  public Truck(@JsonProperty("name") final String name) {
    super();
    this.name = requireNonNull(name);
  }
}
