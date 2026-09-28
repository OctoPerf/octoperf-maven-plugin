package com.octoperf.entity.runtime.k6;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a scenario running the K6 Virtual User {@code virtualUserId} under the load its script declares.
 * {@code options} is the JSON {@code k6 inspect --execution-requirements} printed, passed through untouched: the
 * server reads its {@code scenarios}. {@code entrypointFileName} names the inspected script, whose functions the
 * scenarios' {@code exec} name. A {@code null} name leaves the scenario the Virtual User's name.
 */
@Value
public class K6ScenarioRequest {
  String virtualUserId;
  String entrypointFileName;
  String providerId;
  String location;
  String name;
  JsonNode options;

  public K6ScenarioRequest(
    @JsonProperty("virtualUserId") final String virtualUserId,
    @JsonProperty("entrypointFileName") final String entrypointFileName,
    @JsonProperty("providerId") final String providerId,
    @JsonProperty("location") final String location,
    @JsonProperty("name") final String name,
    @JsonProperty("options") final JsonNode options) {
    super();
    this.virtualUserId = checkNotNull(virtualUserId);
    this.entrypointFileName = checkNotNull(entrypointFileName);
    this.providerId = checkNotNull(providerId);
    this.location = checkNotNull(location);
    this.name = name;
    this.options = checkNotNull(options);
  }
}
