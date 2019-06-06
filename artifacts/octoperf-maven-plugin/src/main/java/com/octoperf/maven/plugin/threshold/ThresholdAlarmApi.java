package com.octoperf.maven.plugin.threshold;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

interface ThresholdAlarmApi {

  @POST("/analysis/threshold-alarms")
  Call<List<ThresholdAlarm>> getAlarms(@Body RequestBody json);
}
