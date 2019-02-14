package com.octoperf.tools.retrofit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

@Configuration
class RetrofitSecurityConfig {

  @Bean
  SecuredRestApiWrapperFactory securedRestApiWrapperFactory(
    final ObjectMapper json,
    final X509TrustManager trustManager,
    final SSLSocketFactory socketFactory) {
    return new OctoperfRetrofitWrapperFactory(
      json,
      trustManager,
      socketFactory
    );
  }
}
