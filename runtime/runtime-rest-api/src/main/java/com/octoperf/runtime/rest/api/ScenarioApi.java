package com.octoperf.runtime.rest.api;

import com.octoperf.entity.analysis.report.BenchReport;
import com.octoperf.entity.runtime.Scenario;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Set;

/**
 * {@link Scenario} REST Service.
 *
 * @author jerome
 *
 */
public interface ScenarioApi {

  @POST("/runtime/scenarios/maven/{workspaceId}/{projectId}")
  Call<Scenario> newScenario(
    @Path("workspaceId") String workspaceId,
    @Path("projectId") String projectId,
    @Body RequestBody body);

  @POST("/runtime/scenarios/run/{id}")
  Call<BenchReport> run(@Path("id") String scenarioId);

  @GET("/runtime/scenarios/by-project/{projectId}")
  Call<List<Scenario>> list(@Path("projectId") String projectId);

  @DELETE("/runtime/scenarios/{id}")
  Call<Void> delete(@Path("id") String scenarioId);
}
