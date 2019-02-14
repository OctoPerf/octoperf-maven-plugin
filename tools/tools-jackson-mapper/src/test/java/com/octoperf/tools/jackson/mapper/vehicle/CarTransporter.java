package com.octoperf.tools.jackson.mapper.vehicle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
public class CarTransporter implements Vehicle {
  String name;
  List<Vehicle> vehicles;

  @JsonCreator
  public CarTransporter(
    @JsonProperty("name") final String name,
    @JsonProperty("vehicles") final List<Vehicle> vehicles) {
    super();
    this.name = requireNonNull(name);
    this.vehicles = requireNonNull(vehicles);
  }
}
