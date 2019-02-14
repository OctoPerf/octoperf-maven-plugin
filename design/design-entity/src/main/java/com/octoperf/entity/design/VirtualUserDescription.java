package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.joda.time.DateTime;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualUserDescription {
  String id;
  String projectId;
  String name;
  String description;

  @JsonCreator
  public VirtualUserDescription(
    @JsonProperty("id") final String id,
    @JsonProperty("projectId") final String projectId,
    @JsonProperty("name") final String name,
    @JsonProperty("description") final String description) {
    super();
    this.id = checkNotNull(id);
    this.projectId = checkNotNull(projectId);
    this.name = checkNotNull(name);
    this.description = checkNotNull(description);
  }

}