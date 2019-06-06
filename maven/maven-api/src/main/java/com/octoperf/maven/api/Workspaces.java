package com.octoperf.maven.api;

import java.io.IOException;

@FunctionalInterface
public interface Workspaces {

  String getWorkspaceId(String name) throws IOException;
}
