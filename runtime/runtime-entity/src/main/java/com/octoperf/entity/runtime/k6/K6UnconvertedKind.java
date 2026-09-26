package com.octoperf.entity.runtime.k6;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

/**
 * Why a part of the K6 load did not cross over to the OctoPerf scenario as it is.
 */
public enum K6UnconvertedKind {
  /** {@code k6 inspect} ran without {@code --execution-requirements}: no {@code scenarios} to read. */
  NO_SCENARIOS,
  /** An arrival-rate scenario: no profile was created for it. */
  OPEN_MODEL,
  /** An executor K6 does not know: no profile was created for it. */
  UNKNOWN_EXECUTOR,
  /** {@code shared-iterations}, approximated as the iterations divided by the VUs. */
  SHARED_ITERATIONS,
  /** The {@code startTime} of an iteration-based scenario was dropped. */
  START_TIME_WITH_ITERATIONS,
  /** The {@code gracefulRampDown} was dropped. */
  GRACEFUL_RAMP_DOWN,
  /** The scenario's {@code tags} were dropped. */
  SCENARIO_TAGS,
  /** A kind a newer server reports. */
  @JsonEnumDefaultValue
  UNKNOWN
}
