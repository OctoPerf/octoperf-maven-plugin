package com.octoperf.runtime.rest.api;

import com.octoperf.entity.runtime.k6.K6ScenarioConversion;
import com.octoperf.entity.runtime.k6.K6ScenarioRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface K6ScenarioApi {

  @POST("/runtime/scenarios/k6/{projectId}")
  Call<K6ScenarioConversion> create(@Path("projectId") String projectId, @Body K6ScenarioRequest request);
}
