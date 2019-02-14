package com.octoperf.maven.rest;


import com.google.common.collect.ImmutableList;
import com.octoperf.design.rest.api.HttpServerApi;
import com.octoperf.entity.design.HttpServer;
import com.octoperf.maven.api.HttpServers;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestHttpServers implements HttpServers {
  @NonNull
  HttpServerApi api;
  @NonNull
  CallService calls;

  @Override
  public void removeAll(final String projectId) {
    calls
      .execute(api.findByProjectId(projectId))
      .orElse(ImmutableList.of())
      .stream()
      .map(HttpServer::getId)
      .map(api::delete)
      .forEach(calls::execute);
  }
}
