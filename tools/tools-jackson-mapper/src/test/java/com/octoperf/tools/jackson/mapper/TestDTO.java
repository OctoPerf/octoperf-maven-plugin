package com.octoperf.tools.jackson.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
public final class TestDTO {
  private final Set<String> values;
  private final TestDTOType type;

  @JsonCreator
  public TestDTO(
    @JsonProperty("values") final Set<String> values,
    @JsonProperty("type") final TestDTOType type) {
    super();
    this.values = checkNotNull(values);
    this.type = checkNotNull(type);
  }

}
