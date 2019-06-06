package com.octoperf.maven.plugin;

import com.octoperf.maven.api.ProjectFiles;
import com.octoperf.maven.api.Projects;
import com.octoperf.maven.api.VirtualUsers;
import com.octoperf.maven.api.Workspaces;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.context.support.GenericApplicationContext;

import java.io.File;
import java.io.IOException;

@Slf4j
@Mojo(name = "import-jmx")
public class ImportJmx extends AbstractOctoPerfMojo {

  @Parameter(defaultValue = "${project.basedir}/src/main/resources")
  private File resourcesFolder;
  @Parameter(defaultValue = "${project.basedir}/script.jmx")
  private File jmxFile;

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

      final ProjectFiles files = context.getBean(ProjectFiles.class);
      files.uploadAll(projectId, resourcesFolder);

      final VirtualUsers virtualUsers = context.getBean(VirtualUsers.class);
      virtualUsers.importJMX(projectId, jmxFile);

    } catch (final IOException e) {
      log.error(e);
      throw new MojoExecutionException("", e);
    }
  }
}
