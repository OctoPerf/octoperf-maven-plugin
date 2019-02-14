package com.octoperf.maven.api;

import java.util.Optional;

@FunctionalInterface
public interface Projects {

  Optional<String> getProjectId(
    String workspaceId,
    String name);
}
