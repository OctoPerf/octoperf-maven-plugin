package com.octoperf.maven.api;

import java.io.File;

public interface VirtualUsers {

  void removeAll(String projectId);

  void importJMX(String projectId, File file);
}
