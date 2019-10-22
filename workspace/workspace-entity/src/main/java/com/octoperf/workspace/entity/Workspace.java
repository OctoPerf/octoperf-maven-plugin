package com.octoperf.workspace.entity;

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
public class Workspace {
  @With
  String id;
  @With
  String name;
  String description;

  @JsonCreator
  Workspace(
    @JsonProperty("id") final String id,
    @JsonProperty("name") final String name,
    @JsonProperty("description") final String description) {
    super();
    this.id = checkNotNull(id);
    this.name = checkNotNull(name);
    this.description = checkNotNull(description);
  }
}
