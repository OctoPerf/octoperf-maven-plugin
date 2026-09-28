package com.octoperf.maven.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.octoperf.entity.design.VirtualUser;
import com.octoperf.entity.runtime.Scenario;
import com.octoperf.entity.runtime.k6.K6ScenarioConversion;
import com.octoperf.entity.runtime.k6.K6ScenarioRequest;
import com.octoperf.entity.runtime.k6.K6Unconverted;
import com.octoperf.maven.api.DockerProviders;
import com.octoperf.maven.api.K6Scenarios;
import com.octoperf.maven.api.Projects;
import com.octoperf.maven.api.VirtualUsers;
import com.octoperf.maven.api.Workspaces;
import com.octoperf.maven.plugin.k6.K6Options;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.context.support.GenericApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Imports a K6 script as a Virtual User, and creates the scenario reproducing the load its {@code options} declare.
 * The options are resolved here, where the script belongs, by {@code k6 inspect --execution-requirements}: the server
 * only converts that JSON, it never runs the script. {@code execute-scenario} then runs the scenario by its name.
 */
@Mojo(name = "import-k6")
public class ImportK6 extends AbstractOctoPerfMojo {

  @Parameter(defaultValue = "${project.basedir}/script.js")
  private File k6Entrypoint;
  @Parameter
  private List<File> k6Modules = new ArrayList<>();
  @Parameter(defaultValue = "k6")
  private String k6Executable = "k6";
  @Parameter
  private File k6OptionsFile;
  @Parameter(defaultValue = "Scenario")
  private String scenarioName = "Scenario";
  @Parameter(required = true)
  private String providerName;
  @Parameter(required = true)
  private String location;
  @Parameter(defaultValue = "true")
  private boolean failOnUnconverted = true;
  @Parameter(defaultValue = "${project.basedir}", readonly = true)
  private File baseDir;

  @Override
  public void execute() throws MojoExecutionException {
    try (GenericApplicationContext context = newContext()) {
      final Log log = getLog();
      final String workspaceId = context.getBean(Workspaces.class).getWorkspaceId(workspaceName);
      final String projectId = context.getBean(Projects.class).getProjectId(workspaceId, projectName);
      final String providerId = context.getBean(DockerProviders.class).getProviderId(workspaceId, providerName, location);
      final JsonNode options = options(context.getBean(K6Options.class));

      final VirtualUser user = context.getBean(VirtualUsers.class).importK6(projectId, k6Entrypoint, k6Modules);
      log.info("Virtual User: " + user.getName() + " (" + user.getId() + ")");

      final K6ScenarioConversion conversion = context
        .getBean(K6Scenarios.class)
        .create(projectId, new K6ScenarioRequest(
          user.getId(), k6Entrypoint.getName(), providerId, location, scenarioName, options));
      report(conversion);
    } catch (final IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  private JsonNode options(final K6Options k6) throws IOException {
    if (k6OptionsFile == null) {
      getLog().info("Resolving the options of " + k6Entrypoint + " with " + k6Executable + " inspect");
      return k6.inspect(executable(), k6Entrypoint);
    }
    getLog().info("Reading the options of " + k6OptionsFile);
    return k6.read(k6OptionsFile);
  }

  /**
   * A bare name is looked up on the PATH; a path is the project's, as every other file parameter, and not relative to
   * the entrypoint's folder k6 runs in.
   */
  private String executable() {
    final File path = new File(k6Executable);
    final boolean bareName = path.getParent() == null;
    return bareName || path.isAbsolute() ? k6Executable : new File(baseDir, k6Executable).getAbsolutePath();
  }

  /**
   * Every entry is logged before the build fails, so a single run lists all that needs deciding.
   */
  private void report(final K6ScenarioConversion conversion) throws MojoExecutionException {
    final Log log = getLog();
    conversion
      .getScenario()
      .map(Scenario::getName)
      .ifPresentOrElse(name -> log.info("Scenario: " + name), () -> log.warn("No scenario created"));
    conversion
      .getUnconverted()
      .forEach(entry -> log.warn("Not converted as it is: " + describe(entry)));
    if (conversion.getScenario().isEmpty() || failOnUnconverted && !conversion.getUnconverted().isEmpty()) {
      throw new MojoExecutionException("The load of the K6 script did not convert as it is: see the warnings above");
    }
  }

  private static String describe(final K6Unconverted entry) {
    final String what = entry.getKind() + " (" + entry.getOutcome() + ")";
    return entry.getScenario().isEmpty() ? what : entry.getScenario() + ": " + what;
  }
}
