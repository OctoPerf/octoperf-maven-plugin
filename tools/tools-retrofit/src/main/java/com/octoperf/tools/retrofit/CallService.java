package com.octoperf.tools.retrofit;

import retrofit2.Call;
import retrofit2.Callback;

import java.io.IOException;

import java.util.Optional;

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

  /**
   * Executes a {@link Call} synchronously and catches any
   * possible {@link java.io.IOException}.
   *
   * @param call asynchronous call
   * @param <T> type of the call
   * @return response returned if any
   */
  <T> Optional<T> execute(Call<T> call, Callback<T> callback);

  /**
   * Executes a {@link Call} synchronously and fails on anything but a successful response carrying a body, so a
   * goal whose request the server refused stops the build instead of going on with nothing.
   *
   * @param call asynchronous call
   * @param <T> type of the call
   * @return the body of the response
   * @throws IOException when the call fails, the server answers an error status (its message included) or no body
   */
  <T> T executeOrThrow(Call<T> call) throws IOException;
}
