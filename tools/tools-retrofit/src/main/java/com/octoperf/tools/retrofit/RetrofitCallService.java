package com.octoperf.tools.retrofit;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
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

  @Override
  public <T> T executeOrThrow(final Call<T> call) throws IOException {
    final Response<T> response = call.execute();
    final Request request = call.request();
    if (response.isSuccessful()) {
      return Optional
        .ofNullable(response.body())
        .orElseThrow(() -> new IOException("Empty response to " + request.method() + " " + request.url()));
    }
    throw new IOException("HTTP " + response.code() + " on " + request.method() + " " + request.url() + ": "
      + errorMessage(response));
  }

  private static String errorMessage(final Response<?> response) throws IOException {
    try (ResponseBody body = response.errorBody()) {
      return body == null ? response.message() : body.string();
    }
  }
}
