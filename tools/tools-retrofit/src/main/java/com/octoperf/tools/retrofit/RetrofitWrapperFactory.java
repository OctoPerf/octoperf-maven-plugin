package com.octoperf.tools.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import static java.util.concurrent.TimeUnit.MINUTES;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RetrofitWrapperFactory implements RestApiWrapperFactory {
  @NonNull
  ObjectMapper objectMapper;
  @NonNull
  X509TrustManager trustManager;
  @NonNull
  SSLSocketFactory socketFactory;

  @Override
  public RestApiWrapper newWrapper(final String baseUrl) {
    final OkHttpClient client = new OkHttpClient.Builder()
      .readTimeout(1, MINUTES)
      .writeTimeout(1, MINUTES)
      .sslSocketFactory(socketFactory, trustManager)
      .hostnameVerifier(new NoopHostnameVerifier())
      .build();
    return newWrapper(client, baseUrl);
  }

  @Override
  public RestApiWrapper newWrapper(
    final OkHttpClient client,
    final String baseUrl) {

    return new RetrofitWrapper(new Retrofit
      .Builder()
      .addConverterFactory(JacksonConverterFactory.create(objectMapper))
      .client(client)
      .baseUrl(baseUrl)
      .build());
  }
}
