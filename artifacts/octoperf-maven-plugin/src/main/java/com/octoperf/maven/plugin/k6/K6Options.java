package com.octoperf.maven.plugin.k6;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

/**
 * The resolved options of a K6 script, which the server converts into a scenario. The script runs here, on the
 * machine it belongs to: the server never executes it.
 */
public interface K6Options {

  /**
   * Runs {@code k6 inspect --execution-requirements} on {@code entrypoint}, which makes K6 derive {@code scenarios}
   * from the {@code vus}, {@code duration}, {@code iterations} and {@code stages} shortcuts.
   *
   * @throws IOException when k6 cannot be started, fails on the script, or prints no JSON object
   */
  JsonNode inspect(String executable, File entrypoint) throws IOException;

  /**
   * Reads options {@code k6 inspect --execution-requirements} printed earlier, for a build without k6.
   */
  JsonNode read(File options) throws IOException;
}
