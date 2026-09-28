package com.octoperf.maven.api;

import com.octoperf.entity.runtime.k6.K6ScenarioConversion;
import com.octoperf.entity.runtime.k6.K6ScenarioRequest;

import java.io.IOException;

@FunctionalInterface
public interface K6Scenarios {

  /**
   * Creates the scenario reproducing the load of a K6 script from its resolved options.
   */
  K6ScenarioConversion create(String projectId, K6ScenarioRequest request) throws IOException;
}
