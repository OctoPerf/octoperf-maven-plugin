package com.octoperf.workspace.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableSet;
import lombok.Value;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

/**
 * A load generator provider a workspace can run on, and the names of its locations: the keys of its
 * {@code regions}, whatever each region holds. A provider saying nothing of {@code enabled} is enabled.
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerProvider {
  String id;
  String name;
  boolean enabled;
  Set<String> locations;

  @JsonCreator
  public DockerProvider(
    @JsonProperty("id") final String id,
    @JsonProperty("name") final String name,
    @JsonProperty("enabled") final Boolean enabled,
    @JsonProperty("regions") final Map<String, JsonNode> regions) {
    super();
    this.id = checkNotNull(id);
    this.name = checkNotNull(name);
    this.enabled = ofNullable(enabled).orElse(true);
    this.locations = ofNullable(regions)
      .map(Map::keySet)
      .map(ImmutableSet::copyOf)
      .orElse(ImmutableSet.of());
  }
}
