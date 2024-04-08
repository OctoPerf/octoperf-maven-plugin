package com.octoperf.maven.rest;

import com.octoperf.analysis.rest.client.BenchReportTemplatesApi;
import com.octoperf.analysis.rest.client.JUnitReportApi;
import com.octoperf.analysis.rest.client.LogApi;
import com.octoperf.analysis.rest.client.MetricsApi;
import com.octoperf.design.rest.api.*;
import com.octoperf.runtime.rest.api.BenchResultsApi;
import com.octoperf.runtime.rest.api.ScenarioApi;
import com.octoperf.task.rest.api.TasksApi;
import com.octoperf.tools.retrofit.security.SecuredRestApiWrapper;
import com.octoperf.workspace.rest.api.WorkspacesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestMavenConfig {

  @Bean
  BenchReportTemplatesApi benchReportTemplatesApi(final SecuredRestApiWrapper w) {
    return w.create(BenchReportTemplatesApi.class);
  }

  @Bean
  HttpServerApi httpServerApi(final SecuredRestApiWrapper w) {
    return w.create(HttpServerApi.class);
  }

  @Bean
  BenchResultsApi benchResultsApi(final SecuredRestApiWrapper w) {
    return w.create(BenchResultsApi.class);
  }

  @Bean
  ProjectFilesApi projectFilesApi(final SecuredRestApiWrapper w) {
    return w.create(ProjectFilesApi.class);
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
  ProjectsApi projectsApi(final SecuredRestApiWrapper w) {
    return w.create(ProjectsApi.class);
  }

  @Bean
  ScenarioApi scenarioApi(final SecuredRestApiWrapper w) {
    return w.create(ScenarioApi.class);
  }

  @Bean
  VirtualUserApi virtualUsersApi(final SecuredRestApiWrapper w) {
    return w.create(VirtualUserApi.class);
  }

  @Bean
  ImportApi importApi(final SecuredRestApiWrapper w) {
    return w.create(ImportApi.class);
  }

  @Bean
  WorkspacesApi workspacesApi(final SecuredRestApiWrapper w) {
    return w.create(WorkspacesApi.class);
  }

  @Bean
  VariablesApi variablesApi(final SecuredRestApiWrapper w) {
    return w.create(VariablesApi.class);
  }

  @Bean
  TasksApi tasksApi(final SecuredRestApiWrapper w) {
    return w.create(TasksApi.class);
  }
}
