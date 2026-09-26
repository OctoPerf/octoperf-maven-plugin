package com.octoperf.entity.runtime.k6;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.octoperf.entity.runtime.Scenario;
import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * The scenario the server created from the K6 options, absent when no K6 scenario converted, and what did not cross
 * over as it is.
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class K6ScenarioConversion {
  Optional<Scenario> scenario;
  List<K6Unconverted> unconverted;

  public K6ScenarioConversion(
    @JsonProperty("scenario") final Scenario scenario,
    @JsonProperty("unconverted") final List<K6Unconverted> unconverted) {
    super();
    this.scenario = ofNullable(scenario);
    this.unconverted = ofNullable(unconverted).map(ImmutableList::copyOf).orElse(ImmutableList.of());
  }
}
