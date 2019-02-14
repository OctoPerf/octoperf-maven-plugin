package com.octoperf.entity.analysis.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchReport {
  String id;
  String projectId;
  List<String> benchResultIds;
  String name;
  String description;

  @JsonCreator
  BenchReport(
    @JsonProperty("id") final String id,
    @JsonProperty("projectId") final String projectId,
    @JsonProperty("benchResultIds") final List<String> benchResultIds,
    @JsonProperty("name") final String name,
    @JsonProperty("description") final String description) {
    super();
    this.id = checkNotNull(id);
    this.projectId = checkNotNull(projectId);
    this.benchResultIds = checkNotNull(benchResultIds);
    this.name = checkNotNull(name);
    this.description = checkNotNull(description);
  }

}
