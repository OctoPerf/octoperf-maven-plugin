package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpServer {
  String id;
  String projectId;

  @JsonCreator
  HttpServer(
    @JsonProperty("id") final String id,
    @JsonProperty("projectId") final String projectId) {
    super();
    this.id = checkNotNull(id);
    this.projectId = checkNotNull(projectId);
  }
}
