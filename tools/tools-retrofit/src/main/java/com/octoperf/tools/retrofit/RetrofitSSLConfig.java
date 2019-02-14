package com.octoperf.tools.retrofit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;

@Configuration
public class RetrofitSSLConfig {

  @Bean
  public SSLSocketFactory socketFactory() {
    return (SSLSocketFactory) SSLSocketFactory.getDefault();
  }

}
