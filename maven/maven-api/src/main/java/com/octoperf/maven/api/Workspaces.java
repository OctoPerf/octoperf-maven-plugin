package com.octoperf.maven.api;

import java.util.Optional;

@FunctionalInterface
public interface Workspaces {

  Optional<String> getWorkspaceId(String name);
}
