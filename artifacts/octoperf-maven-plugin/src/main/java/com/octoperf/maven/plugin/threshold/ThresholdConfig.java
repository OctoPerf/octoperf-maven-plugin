package com.octoperf.maven.plugin.threshold;

import com.octoperf.tools.retrofit.security.SecuredRestApiWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ThresholdConfig {

  @Bean
  ThresholdAlarmApi thresholdAlarmApi(final SecuredRestApiWrapper w) {
    return w.create(ThresholdAlarmApi.class);
  }
}
