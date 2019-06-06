package com.octoperf.entity.runtime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.nullToEmpty;


@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Scenario {
  String id;
  String projectId;
  String name;
  String description;

  @JsonCreator
  Scenario(@JsonProperty("id") final String id,
           @JsonProperty("projectId") final String projectId,
           @JsonProperty("name") final String name,
           @JsonProperty("description") final String description) {
    super();
    this.id = nullToEmpty(id);
    this.projectId = nullToEmpty(projectId);
    this.name = checkNotNull(name);
    this.description = nullToEmpty(description);
  }
}
