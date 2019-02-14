package com.octoperf.tools.retrofit.security;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import retrofit2.Retrofit;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * Retrofit wrapper. As retrofit is a final class this is mandatory to unit test our code ...
 */
@FieldDefaults(makeFinal=true, level= PRIVATE)
@AllArgsConstructor(access = PACKAGE)
final class OctoperfRetrofitWrapper implements SecuredRestApiWrapper {
  @NonNull
  Retrofit retrofit;
  @NonNull
  RestClientAuthenticator authenticator;

  @Override
  public <T> T create(final Class<T> service) {
    return retrofit.create(service);
  }

  @Override
  public RestClientAuthenticator authenticator() {
    return authenticator;
  }
}
