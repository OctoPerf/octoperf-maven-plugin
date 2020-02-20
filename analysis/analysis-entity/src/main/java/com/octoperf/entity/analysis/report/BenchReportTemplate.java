package com.octoperf.entity.analysis.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchReportTemplate {
  String id;
  String name;

  @JsonCreator
  BenchReportTemplate(
    @JsonProperty("id") final String id,
    @JsonProperty("name") final String name) {
    super();
    this.id = requireNonNull(id);
    this.name = requireNonNull(name);
  }
}
