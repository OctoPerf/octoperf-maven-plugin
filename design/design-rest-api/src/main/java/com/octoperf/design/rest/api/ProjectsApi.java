package com.octoperf.design.rest.api;

import com.octoperf.entity.design.Project;
import com.octoperf.entity.design.ProjectType;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ProjectsApi {

  @GET("/design/projects/by-workspace/{workspaceId}/{type}")
  Call<List<Project>> list(@Path("workspaceId") String workspaceId, @Path("type") ProjectType type);

  @DELETE("/design/projects/{id}")
  Call<Void> delete(@Path("id") String id);
}
