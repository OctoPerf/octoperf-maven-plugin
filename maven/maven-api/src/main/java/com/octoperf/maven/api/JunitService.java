package com.octoperf.maven.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface JunitService {

  /**
   * Saves the junit test report on disk.
   *
   * @param benchResultId bench result id
   * @return the path where the file is stored
   * @throws IOException
   */
  Path saveJUnitReport(
    File folder,
    String benchResultId) throws IOException;
}
