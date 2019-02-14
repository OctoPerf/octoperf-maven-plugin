package com.octoperf.runtime.rest.api;

import com.octoperf.commons.entity.ValueWrapper;
import com.octoperf.entity.runtime.BenchResult;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * {@link BenchResult} REST Service.
 *
 * @author jerome
 *
 */
public interface BenchResultsApi {

  @GET("/runtime/bench-results/{id}")
  Call<BenchResult> find(@Path("id") String id);

  @POST("/runtime/bench-results/stop/{id}")
  Call<BenchResult> stop(@Path("id") String benchId);

  @GET("/runtime/bench-results/progress/{benchResultId}")
  Call<ValueWrapper<Double>> getProgress(@Path("benchResultId") String benchResultId);
}
