package com.octoperf.design.rest.api;

import com.octoperf.entity.design.VirtualUser;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import java.util.List;

/**
 * REST dao to import HAR file into the design project.
 *
 * @author jerome
 */
public interface ImportApi {

  @Multipart
  @POST("/design/imports/jmx/{projectId}")
  Call<ResponseBody> importJMX(@Path("projectId") String projectId, @Part MultipartBody.Part file);
}
