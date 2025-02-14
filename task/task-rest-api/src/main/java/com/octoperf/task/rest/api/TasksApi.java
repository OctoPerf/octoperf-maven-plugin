package com.octoperf.task.rest.api;

import com.octoperf.task.entity.Task;
import com.octoperf.task.entity.TaskResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TasksApi {

  @POST("/tasks")
  Call<ResponseBody> submit(@Body final Task content);

  @GET("/tasks/{id}/result")
  Call<TaskResult> getResult(@Path("id") final String id);
}
