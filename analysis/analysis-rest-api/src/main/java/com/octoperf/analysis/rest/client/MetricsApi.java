package com.octoperf.analysis.rest.client;

import com.octoperf.entity.analysis.report.graph.MetricValues;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MetricsApi {

  String PATH = "/analysis/metrics";

  @GET(PATH + "/global/{benchResultId}")
  Call<MetricValues> getGlobalMetrics(@Path("benchResultId") String benchResultId);
}
