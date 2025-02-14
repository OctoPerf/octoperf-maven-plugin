package com.octoperf.tools.retrofit;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Slf4j
final class RetrofitCallService implements CallService {

  @Override
  public <T> Optional<T> execute(final Call<T> call) {
    return execute(call, new LoggingCallBack<>());
  }

  @Override
  public <T> Optional<T> execute(final Call<T> call, final Callback<T> callback) {
    Optional<Response<T>> response = empty();
    try {
      response = of(call.execute());
      response.ifPresent(r -> callback.onResponse(call, r));
      return response.map(Response::body);
    } catch (final IOException e) {
      callback.onFailure(call, e);

      return empty();
    } finally {
      response
        .map(Response::errorBody)
        .ifPresent(ResponseBody::close);
    }
  }
}
