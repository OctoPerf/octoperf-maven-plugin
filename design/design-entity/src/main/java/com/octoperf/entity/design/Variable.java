package com.octoperf.entity.design;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The variable Wrapper is used to encapsulate a variable to be stored on the couchbase database.
 * 
 * @author jerome
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variable {
  @With
  String id;

  @JsonCreator
  public Variable(@JsonProperty("id") final String id) {
    super();
    this.id = checkNotNull(id);
  }
}
