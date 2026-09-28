package com.octoperf.maven.api;

import com.octoperf.entity.design.VirtualUser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface VirtualUsers {

  void removeAll(String projectId);

  void importJMX(String projectId, File file) throws IOException;

  /**
   * Imports a K6 script as a Virtual User: {@code entrypoint} first, then the {@code modules} it imports, each sent
   * under its path relative to the entrypoint's folder, the name the scripts import it by.
   */
  VirtualUser importK6(String projectId, File entrypoint, List<File> modules) throws IOException;
}
