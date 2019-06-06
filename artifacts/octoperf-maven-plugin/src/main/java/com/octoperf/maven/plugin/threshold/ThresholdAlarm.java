package com.octoperf.maven.plugin.threshold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@JsonIgnoreProperties(ignoreUnknown=true)
public class ThresholdAlarm {

  String id;
  ThresholdSeverity severity;

  @JsonCreator
  ThresholdAlarm(
    @JsonProperty("id") final String id,
    @JsonProperty("severity") final ThresholdSeverity severity) {
    super();
    this.id = checkNotNull(id);
    this.severity = checkNotNull(severity);
  }
}
