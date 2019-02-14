package com.octoperf.design.rest.api;

import com.octoperf.entity.design.VirtualUser;
import com.octoperf.entity.design.VirtualUserDescription;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * REST Api to perform operations on {@link VirtualUser}.
 *
 * @author jerome
 *
 */
public interface VirtualUserApi {

  @GET("/design/virtual-users/description/by-project/{projectId}")
  Call<List<VirtualUserDescription>> listDescriptions(@Path("projectId") String projectId);

  @DELETE("/design/virtual-users/{id}")
  Call<Void> delete(@Path("id") String id);
}
