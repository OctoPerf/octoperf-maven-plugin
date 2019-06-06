package com.octoperf.maven.rest;

import com.google.common.collect.ImmutableList;
import com.octoperf.maven.api.Workspaces;
import com.octoperf.tools.retrofit.CallService;
import com.octoperf.workspace.entity.Workspace;
import com.octoperf.workspace.rest.api.WorkspacesApi;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestWorkspaces implements Workspaces {
  @NonNull
  WorkspacesApi api;
  @NonNull
  CallService calls;

  @Override
  public String getWorkspaceId(final String name) throws IOException {
    return calls
      .execute(api.memberOf())
      .orElse(ImmutableList.of())
      .stream()
      .filter(w -> Objects.equals(w.getName(), name))
      .findFirst()
      .map(Workspace::getId)
      .orElseThrow(() -> new IOException("Could not find workspace '" + name + "'"));
  }
}
