package com.octoperf.tools.retrofit;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Slf4j
final class LoggingCallBack<T> implements Callback<T> {

  @Override
  public void onResponse(final Call<T> call, final Response<T> response) {
    // Nothing to do
  }

  @Override
  public void onFailure(final Call<T> call, final Throwable t) {
    final Request request = call.request();
    log.error("[Retrofit] url=" + request.url(), t);
  }
}