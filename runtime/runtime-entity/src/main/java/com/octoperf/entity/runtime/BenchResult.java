package com.octoperf.entity.runtime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchResult {
  @With
  String id;
  String batchId;
  String scenarioId;
  String designProjectId;
  String resultProjectId;
  BenchResultState state;

  @JsonCreator
  BenchResult(
    @JsonProperty("id") final String id,
    @JsonProperty("batchId") final String batchId,
    @JsonProperty("scenarioId") final String scenarioId,
    @JsonProperty("designProjectId") final String designProjectId,
    @JsonProperty("resultProjectId") final String resultProjectId,
    @JsonProperty("state") final BenchResultState state) {
    super();
    this.id = checkNotNull(id);
    this.batchId = checkNotNull(batchId);
    this.scenarioId = checkNotNull(scenarioId);
    this.designProjectId = checkNotNull(designProjectId);
    this.resultProjectId = checkNotNull(resultProjectId);
    this.state = checkNotNull(state);
  }
}
