package com.octoperf.tools.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface TestApi {

  @GET("/test/{id}")
  Call<Void> test(@Path("id") String id);
}
