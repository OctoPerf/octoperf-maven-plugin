package com.octoperf.workspace.rest.api;

import com.octoperf.workspace.entity.DockerProvider;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface DockerProvidersApi {

  @GET("/workspaces/docker-providers/by-workspace/{workspaceId}")
  Call<List<DockerProvider>> byWorkspace(@Path("workspaceId") String workspaceId);

  @GET("/workspaces/docker-providers/public")
  Call<List<DockerProvider>> publicProviders();
}
