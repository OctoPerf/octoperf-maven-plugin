package com.octoperf.maven.rest;

import com.octoperf.maven.api.DockerProviders;
import com.octoperf.tools.retrofit.CallService;
import com.octoperf.workspace.entity.DockerProvider;
import com.octoperf.workspace.rest.api.DockerProvidersApi;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestDockerProviders implements DockerProviders {
  @NonNull
  DockerProvidersApi api;
  @NonNull
  CallService calls;

  @Override
  public String getProviderId(final String workspaceId, final String name, final String location) throws IOException {
    final List<DockerProvider> providers = Stream
      .concat(
        calls.executeOrThrow(api.byWorkspace(workspaceId)).stream(),
        calls.executeOrThrow(api.publicProviders()).stream())
      .filter(DockerProvider::isEnabled)
      .toList();
    final DockerProvider provider = providers
      .stream()
      .filter(candidate -> candidate.getName().equals(name))
      .findFirst()
      .orElseThrow(() -> new IOException("No enabled provider named '" + name + "'. Available: " + names(providers)));
    if (provider.getLocations().contains(location)) {
      return provider.getId();
    }
    throw new IOException("Provider '" + name + "' has no location '" + location + "'. Its locations: "
      + String.join(", ", provider.getLocations().stream().sorted().toList()));
  }

  private static String names(final List<DockerProvider> providers) {
    return providers
      .stream()
      .map(DockerProvider::getName)
      .sorted()
      .collect(joining(", "));
  }
}
