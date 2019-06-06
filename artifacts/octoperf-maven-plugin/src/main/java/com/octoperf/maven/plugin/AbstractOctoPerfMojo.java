package com.octoperf.maven.plugin;

import com.octoperf.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.File;

@Slf4j
public abstract class AbstractOctoPerfMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.build.directory}", readonly = true)
  protected File buildDir;
  @Parameter(defaultValue = "https://api.octoperf.com")
  protected String serverUrl = "";
  @Parameter(defaultValue = "Default")
  protected String workspaceName = "";
  @Parameter(defaultValue = "Maven", required = true)
  protected String projectName = "";
  @Parameter(defaultValue = "Scenario")
  protected String scenarioName = "";
  @Parameter(required = true)
  protected String apiKey = "";

  protected final GenericApplicationContext newContext() {
    StaticLoggerBinder.getSingleton().setMavenLog(getLog());

    System.setProperty("apiKey", apiKey);
    System.setProperty("serverUrl", serverUrl);
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(Application.class);
    context.refresh();
    return context;
  }
}
