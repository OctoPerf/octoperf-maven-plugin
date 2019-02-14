package com.octoperf.entity.analysis.report.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
public class MetricValue {
  String name;
  double value;
  String unit;
  
  @JsonCreator
  public MetricValue(
      @JsonProperty("name") final String name, 
      @JsonProperty("value") final double value, 
      @JsonProperty("unit") final String unit) {
    super();
    this.name = checkNotNull(name);
    this.value = checkNotNull(value);
    this.unit = checkNotNull(unit);
  }
  
}
