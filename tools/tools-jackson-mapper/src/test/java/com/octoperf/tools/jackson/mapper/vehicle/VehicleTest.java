package com.octoperf.tools.jackson.mapper.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class VehicleTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.registerSubtypes(new NamedType(Truck.class, "Truck"));
    MAPPER.registerSubtypes(new NamedType(Car.class, "Car"));
    MAPPER.registerSubtypes(new NamedType(CarTransporter.class, "CarTransporter"));
  }

  @Test
  public void shouldSerializeCarTransporter() throws IOException {
    final Vehicle transporter = new CarTransporter("Transporter", ImmutableList.of(new Car("Dodge"), new Truck("Scania")));
    final String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(transporter);
    final Vehicle read = MAPPER.readValue(json, Vehicle.class);
    assertEquals(transporter, read);
  }

  @Test
  public void shouldSerializeVehicles() throws IOException {
    final Vehicles vehicles = new Vehicles(ImmutableList.of(new Car("Dodge"), new Truck("Scania")));
    final String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(vehicles);
    final Vehicles read = MAPPER.readValue(json, Vehicles.class);
    assertEquals(vehicles, read);
  }

}
