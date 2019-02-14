package com.octoperf.maven.api;

import java.io.File;

public interface ProjectFiles {

  void removeAll(String projectId);

  void uploadAll(String projectId, File folder);
}
