package com.octoperf.tools.jackson.mapper.vehicle;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=Truck.class, name = "Truck"),
  @JsonSubTypes.Type(value=Car.class, name = "Car")
})
public interface Vehicle {

  String getName();
}
