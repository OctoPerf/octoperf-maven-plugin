package com.octoperf.maven.rest;

import com.google.common.collect.ImmutableList;
import com.octoperf.design.rest.api.ProjectsApi;
import com.octoperf.entity.design.Project;
import com.octoperf.maven.api.Projects;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.octoperf.entity.design.ProjectType.DESIGN;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestProjects implements Projects {
  @NonNull
  ProjectsApi api;
  @NonNull
  CallService calls;

  @Override
  public String getProjectId(
    final String workspaceId,
    final String name) throws IOException {
    return calls
      .execute(api.list(workspaceId, DESIGN))
      .orElse(ImmutableList.of())
      .stream()
      .filter(p -> Objects.equals(p.getName(), name))
      .map(Project::getId)
      .findFirst()
      .orElseThrow(() -> new IOException("Could not find project with name='" + name + "'"));
  }
}
