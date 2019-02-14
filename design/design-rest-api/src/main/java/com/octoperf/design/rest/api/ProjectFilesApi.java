package com.octoperf.design.rest.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Set;

/**
 * File storage linked to a specific project. The storage is intended to store third party files
 * containing information used by the load testing tool.
 *
 * @author jerome
 *
 */
public interface ProjectFilesApi {

  @GET("/design/project-files/list/{projectId}")
  Call<Set<String>> listFiles(@Path("projectId") String projectId);

  @DELETE("/design/project-files/{projectId}")
  Call<Void> delete(@Path("projectId") String projectId, @Query("filename") String filename);

  @Multipart
  @POST("/design/project-files/{projectId}")
  Call<Void> upload(@Path("projectId") String projectId, @Part("filename") String filename,
                    @Part MultipartBody.Part file);
}
