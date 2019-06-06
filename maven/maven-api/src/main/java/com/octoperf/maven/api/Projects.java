package com.octoperf.maven.api;

import java.io.IOException;

@FunctionalInterface
public interface Projects {

  String getProjectId(
    String workspaceId,
    String name) throws IOException;
}
