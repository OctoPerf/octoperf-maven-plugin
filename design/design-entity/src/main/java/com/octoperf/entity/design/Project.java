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
public class Project {
  String id;
  String workspaceId;
  String name;
  String description;

  @JsonCreator
  Project(
    @JsonProperty("id") final String id,
    @JsonProperty("workspaceId") final String workspaceId,
    @JsonProperty("name") final String name,
    @JsonProperty("description") final String description) {
    super();
    this.id = checkNotNull(id);
    this.workspaceId = checkNotNull(workspaceId);
    this.name = checkNotNull(name);
    this.description = checkNotNull(description);
  }

}
