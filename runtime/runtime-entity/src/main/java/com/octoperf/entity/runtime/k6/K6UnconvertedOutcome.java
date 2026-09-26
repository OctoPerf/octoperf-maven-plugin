package com.octoperf.entity.runtime.k6;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

/**
 * What became of the K6 scenario an entry names.
 */
public enum K6UnconvertedOutcome {
  /** No profile was created for the scenario. */
  NOT_CONVERTED,
  /** The profile runs a load close to the scenario's. */
  APPROXIMATED,
  /** The profile runs the scenario's load, without the setting the entry names. */
  IGNORED,
  /** An outcome a newer server reports. */
  @JsonEnumDefaultValue
  UNKNOWN
}
