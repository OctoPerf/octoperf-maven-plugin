package com.octoperf.analysis.rest.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Set;

public interface LogApi {

  @GET("/analysis/logs/list/{benchResultId}")
  Call<Set<String>> getFiles(@Path("benchResultId") String benchResultId);

  @GET("/analysis/logs/{benchResultId}")
  Call<ResponseBody> getFile(
          @Path("benchResultId") String benchResultId,
          @Query("filename") String filename);
}
