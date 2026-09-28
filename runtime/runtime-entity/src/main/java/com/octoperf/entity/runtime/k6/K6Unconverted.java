package com.octoperf.entity.runtime.k6;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Optional.ofNullable;

/**
 * A part of the load of the K6 scenario {@code scenario} that did not cross over as it is; {@code scenario} is empty
 * when the entry concerns the options as a whole.
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class K6Unconverted {
  String scenario;
  K6UnconvertedKind kind;
  K6UnconvertedOutcome outcome;

  public K6Unconverted(
    @JsonProperty("scenario") final String scenario,
    @JsonProperty("kind") @JsonFormat(with = READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE) final K6UnconvertedKind kind,
    @JsonProperty("outcome") @JsonFormat(with = READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
    final K6UnconvertedOutcome outcome) {
    super();
    this.scenario = nullToEmpty(scenario);
    this.kind = checkNotNull(kind);
    this.outcome = ofNullable(outcome).orElse(K6UnconvertedOutcome.UNKNOWN);
  }
}
