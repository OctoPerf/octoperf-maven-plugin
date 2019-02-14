package com.octoperf.tools.retrofit.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = PACKAGE)
final class APIKeyRestClientAuthenticator implements RestClientAuthenticator {
  private static final String BEARER = "Bearer ";

  @NonNull
  String apiKey;

  @Override
  public Response intercept(final Chain chain) throws IOException {
    final Request request = chain
      .request()
      .newBuilder()
      .addHeader(AUTHORIZATION, BEARER + apiKey)
      .build();
    return chain.proceed(request);
  }
}

