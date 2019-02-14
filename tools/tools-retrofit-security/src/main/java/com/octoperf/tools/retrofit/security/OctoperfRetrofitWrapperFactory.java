package com.octoperf.tools.retrofit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octoperf.tools.retrofit.NoopHostnameVerifier;
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
final class OctoperfRetrofitWrapperFactory implements SecuredRestApiWrapperFactory {
  @NonNull
  ObjectMapper objectMapper;
  @NonNull
  X509TrustManager trustManager;
  @NonNull
  SSLSocketFactory socketFactory;

  @Override
  public SecuredRestApiWrapper newWrapper(final String baseUrl, final String apiKey) {
    final APIKeyRestClientAuthenticator interceptor = new APIKeyRestClientAuthenticator(apiKey);

    final OkHttpClient.Builder builder = new OkHttpClient
      .Builder()
      .readTimeout(1, MINUTES)
      .writeTimeout(1, MINUTES)
      .addNetworkInterceptor(interceptor)
      .hostnameVerifier(new NoopHostnameVerifier())
      .sslSocketFactory(socketFactory, trustManager);

    return new OctoperfRetrofitWrapper(new Retrofit
      .Builder()
      .addConverterFactory(JacksonConverterFactory.create(objectMapper))
      .client(builder.build())
      .baseUrl(baseUrl)
      .build(), interceptor);
  }
}
