package com.octoperf.workspace.entity;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DockerProviderJsonTest {

  @Test
  void shouldNameTheLocationsAfterTheRegions() throws IOException {
    final DockerProvider provider = JsonMapper.builder().build().readValue("""
      {"id": "p", "name": "OctoPerf", "regions": {"eu-west-1": {"label": "Paris"}, "us-east-1": {}}, "type": "X"}
      """, DockerProvider.class);
    assertEquals(Set.of("eu-west-1", "us-east-1"), provider.getLocations());
  }

  @Test
  void shouldHaveNoLocationWithoutRegions() throws IOException {
    final DockerProvider provider = JsonMapper.builder().build().readValue("""
      {"id": "p", "name": "OctoPerf"}
      """, DockerProvider.class);
    assertEquals(Set.of(), provider.getLocations());
  }
}
