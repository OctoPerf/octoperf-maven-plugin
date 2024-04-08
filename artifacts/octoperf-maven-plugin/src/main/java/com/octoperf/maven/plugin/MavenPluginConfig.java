package com.octoperf.maven.plugin;

import com.octoperf.tools.retrofit.security.SecuredRestApiWrapper;
import com.octoperf.tools.retrofit.security.SecuredRestApiWrapperFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MavenPluginConfig {

  @Bean
  SecuredRestApiWrapper apiWrapper(
    @Value("${apiKey}") final String apiKey,
    @Value("${serverUrl}") final String serverUrl,
    final SecuredRestApiWrapperFactory factory) {
    return factory.newWrapper(serverUrl, apiKey);
  }
}
