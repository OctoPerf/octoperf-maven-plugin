package com.octoperf.maven.rest;

import com.octoperf.analysis.rest.client.JUnitReportApi;
import com.octoperf.analysis.rest.client.LogApi;
import com.octoperf.analysis.rest.client.MetricsApi;
import com.octoperf.design.rest.api.*;
import com.octoperf.runtime.rest.api.BenchResultsApi;
import com.octoperf.runtime.rest.api.ScenarioApi;
import com.octoperf.tools.retrofit.security.SecuredRestApiWrapper;
import com.octoperf.workspace.rest.api.WorkspacesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestMavenConfig {

  @Bean
  HttpServerApi httpServerApi(final SecuredRestApiWrapper wrapper) {
    return wrapper.create(HttpServerApi.class);
  }

  @Bean
  BenchResultsApi benchResultsApi(final SecuredRestApiWrapper w) {
    return w.create(BenchResultsApi.class);
  }

  @Bean
  ProjectFilesApi projectFilesApi(final SecuredRestApiWrapper wrapper) {
    return wrapper.create(ProjectFilesApi.class);
  }

  @Bean
  JUnitReportApi jUnitReportApi(final SecuredRestApiWrapper w) {
    return w.create(JUnitReportApi.class);
  }

  @Bean
  LogApi logApi(final SecuredRestApiWrapper w) {
    return w.create(LogApi.class);
  }

  @Bean
  MetricsApi metricsApi(final SecuredRestApiWrapper w) {
    return w.create(MetricsApi.class);
  }

  @Bean
  ProjectsApi projectsApi(final SecuredRestApiWrapper wrapper) {
    return wrapper.create(ProjectsApi.class);
  }

  @Bean
  ScenarioApi scenarioApi(final SecuredRestApiWrapper w) {
    return w.create(ScenarioApi.class);
  }

  @Bean
  VirtualUserApi virtualUsersApi(final SecuredRestApiWrapper wrapper) {
    return wrapper.create(VirtualUserApi.class);
  }

  @Bean
  ImportApi importApi(final SecuredRestApiWrapper wrapper) {
    return wrapper.create(ImportApi.class);
  }

  @Bean
  WorkspacesApi workspacesApi(final SecuredRestApiWrapper wrapper) {
    return wrapper.create(WorkspacesApi.class);
  }
}
