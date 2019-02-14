package com.octoperf.analysis.rest.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JUnitReportApi {

  @GET("/analysis/junit-reports/{benchResultId}")
  Call<ResponseBody> getReport(@Path("benchResultId") String benchResultId);
}
