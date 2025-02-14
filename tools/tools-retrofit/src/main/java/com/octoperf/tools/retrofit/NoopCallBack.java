package com.octoperf.tools.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class NoopCallBack<T> implements Callback<T> {
  
  @Override
  public void onResponse(final Call<T> call, final Response<T> response) {
    // Nothing to do
  }

  @Override
  public void onFailure(final Call<T> call, final Throwable t) {
    // Nothing to do
  }
}