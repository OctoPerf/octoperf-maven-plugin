package com.octoperf.design.rest.api;

import com.octoperf.entity.design.Variable;
import com.octoperf.entity.design.VirtualUser;
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
public interface VariablesApi {

  @GET("/design/variables/by-project/{projectId}")
  Call<List<Variable>> list(@Path("projectId") String projectId);

  @DELETE("/design/variables/{id}")
  Call<Void> delete(@Path("id") String id);
}
