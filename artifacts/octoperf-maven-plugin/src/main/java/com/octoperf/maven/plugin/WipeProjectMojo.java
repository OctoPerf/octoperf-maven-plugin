package com.octoperf.maven.plugin;

import com.octoperf.maven.api.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

@Slf4j
@Mojo(name = "wipe-project")
public class WipeProjectMojo extends AbstractOctoPerfMojo {

  @Override
  public void execute() throws MojoExecutionException {
    final GenericApplicationContext context = newContext();
    try {
      final Workspaces workspaces = context.getBean(Workspaces.class);
      log.info("Workspace: " + workspaceName);
      final String workspaceId = workspaces.getWorkspaceId(workspaceName);

      final Projects projects = context.getBean(Projects.class);
      log.info("Project: " + projectName);
      final String projectId = projects.getProjectId(workspaceId, projectName);

      final Scenarios scenarios = context.getBean(Scenarios.class);
      log.info("Wiping scenarios...");
      scenarios.removeAll(projectId);

      final VirtualUsers virtualUsers = context.getBean(VirtualUsers.class);
      log.info("Wiping virtual users...");
      virtualUsers.removeAll(projectId);

      final HttpServers servers = context.getBean(HttpServers.class);
      log.info("Wiping servers...");
      servers.removeAll(projectId);

      final Variables variables = context.getBean(Variables.class);
      log.info("Wiping variables...");
      variables.removeAll(projectId);

      final ProjectFiles files = context.getBean(ProjectFiles.class);
      log.info("Wiping project files...");
      files.removeAll(projectId);
    } catch (final IOException e) {
      log.error("", e);
      throw new MojoExecutionException("", e);
    } finally {
      context.close();
    }
  }
}
