package com.octoperf.analysis.rest.client;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Set;

public interface LogApi {

  @GET("/analysis/logs/list/{benchResultId}")
  Call<Set<String>> getFiles(@Path("benchResultId") String benchResultId);

  @GET("/analysis/logs/{benchResultId}")
  Call<ResponseBody> getFile(
          @Path("benchResultId") String benchResultId,
          @Query("filename") String filename);

  @GET("/analysis/logs/{benchResultId}/{lines}")
  Call<Void> cat(
          @Path("benchResultId") String benchResultId,
          @Path("lines") int lines,
          @Query("filename") String filename);

  @Multipart
  @POST("/analysis/logs/{benchResultId}")
  Call<Void> uploadFile(
          @Path("benchResultId") String benchResultId,
          @Part MultipartBody.Part file);

  @GET("/analysis/logs/zip/{benchResultId}")
  Call<Void> getZipped(@Path("benchResultId") String benchResultId);

  @DELETE("/analysis/logs/{benchResultId}")
  Call<Void> delete(@Path("benchResultId") String benchResultId, @Query("filename") String filename);
}
