package com.octoperf.tools.retrofit;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@Slf4j
final class RetrofitCallService implements CallService {

  @Override
  public <T> Optional<T> execute(final Call<T> call) {
    try {
      final Response<T> response = call.execute();
      return Optional.ofNullable(response.body());
    } catch (final IOException e) {
      log.error("Error while calling Rest API", e);
      return Optional.empty();
    }
  }
}
