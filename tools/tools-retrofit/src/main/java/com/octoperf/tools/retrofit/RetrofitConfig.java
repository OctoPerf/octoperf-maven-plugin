package com.octoperf.tools.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

@Configuration
class RetrofitConfig {

  @Bean
  X509TrustManager x509TrustManager() {
    return new TrustingX509TrustManager();
  }

  @Bean
  RestApiWrapperFactory restApiWrapperFactory(
    final ObjectMapper objectMapper,
    final X509TrustManager trustManager,
    final SSLSocketFactory socketFactory) {
    return new RetrofitWrapperFactory(objectMapper, trustManager, socketFactory);
  }

  @Bean
  CallService callService() {
    return new RetrofitCallService();
  }
}
