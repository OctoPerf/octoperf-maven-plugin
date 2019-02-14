package com.octoperf.design.rest.api;

import com.octoperf.entity.design.HttpServer;
import com.octoperf.entity.design.Project;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Set;

/**
 * REST Api to perform operations on {@link Project}.
 * 
 * @author jerome
 *
 */
public interface HttpServerApi {

  @GET("/design/http-servers/by-project/{projectId}")
  Call<List<HttpServer>> findByProjectId(@Path("projectId") String projectId);

  @POST("/design/http-servers")
  Call<HttpServer> create(@Body HttpServer dto);

  @PUT("/design/http-servers/{id}")
  Call<HttpServer> update(@Path("id") String id, @Body HttpServer dto);

  @DELETE("/design/http-servers/{id}")
  Call<Void> delete(@Path("id") String id);

  @POST("/design/http-servers/by-project/{projectId}")
  Call<Void> bulkDelete(@Path("projectId") String projectId, @Body Set<HttpServer> servers);

  @POST("/design/http-servers/by-projects")
  Call<List<HttpServer>> byProjects(@Body Set<String> projectIds);
}
