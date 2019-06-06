package com.octoperf.maven.plugin;

import com.octoperf.entity.runtime.Scenario;
import com.octoperf.maven.api.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.context.support.GenericApplicationContext;

import java.io.File;
import java.io.IOException;

@Slf4j
@Mojo(name = "import-scenario")
public class ImportScenario extends AbstractOctoPerfMojo {

  @Parameter(defaultValue = "${project.basedir}/scenario.json")
  private File scenarioFile;

  @Override
  public void execute() throws MojoExecutionException {
    final GenericApplicationContext context = newContext();

    final Log log = getLog();
    try {
      final Workspaces workspaces = context.getBean(Workspaces.class);
      log.info("Workspace: " + workspaceName);
      final String workspaceId = workspaces.getWorkspaceId(workspaceName);

      final Projects projects = context.getBean(Projects.class);
      log.info("Project: " + projectName);
      final String projectId = projects.getProjectId(workspaceId, projectName);

      final Scenarios scenarios = context.getBean(Scenarios.class);
      final Scenario scenario = scenarios.createFromFile(
        workspaceId,
        projectId,
        scenarioFile
      );
      scenarios.log(scenario);

    } catch (final IOException e) {
      log.error(e);
      throw new MojoExecutionException("", e);
    }
  }
}
