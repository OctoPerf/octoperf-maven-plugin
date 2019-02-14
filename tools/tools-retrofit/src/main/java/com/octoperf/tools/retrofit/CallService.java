package com.octoperf.tools.retrofit;

import retrofit2.Call;

import java.util.Optional;

@FunctionalInterface
public interface CallService {

  /**
   * Executes a {@link Call} synchronously and catches any
   * possible {@link java.io.IOException}.
   *
   * @param call asynchronous call
   * @param <T> type of the call
   * @return response returned if any
   */
  <T> Optional<T> execute(Call<T> call);


}
