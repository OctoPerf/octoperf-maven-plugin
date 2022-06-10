package com.octoperf.maven.rest;


import com.octoperf.design.rest.api.VariablesApi;
import com.octoperf.entity.design.Variable;
import com.octoperf.maven.api.Variables;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import static java.util.List.of;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestVariables implements Variables {
  @NonNull
  VariablesApi api;
  @NonNull
  CallService calls;

  @Override
  public void removeAll(final String projectId) {
    calls
      .execute(api.list(projectId))
      .orElse(of())
      .stream()
      .map(Variable::getId)
      .map(api::delete)
      .forEach(calls::execute);
  }
}
