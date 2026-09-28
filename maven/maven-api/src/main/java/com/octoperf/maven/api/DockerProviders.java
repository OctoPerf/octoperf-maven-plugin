package com.octoperf.maven.api;

import java.io.IOException;

@FunctionalInterface
public interface DockerProviders {

  /**
   * The id of the provider named {@code name} the workspace can run on, its own providers first, then the public ones.
   *
   * @throws IOException when no provider has that name, or it has no location {@code location}
   */
  String getProviderId(String workspaceId, String name, String location) throws IOException;
}
