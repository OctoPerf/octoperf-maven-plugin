package com.octoperf.tools.jackson.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;

@Configuration
public class JacksonConfig {

  @Bean
  @Primary
  ObjectMapper objectMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.setSerializationInclusion(NON_NULL);
    mapper.enable(SORT_PROPERTIES_ALPHABETICALLY);
    return mapper;
  }

  @Bean
  JsonMapperService jsonMapperService(final ObjectMapper mapper, final Set<JsonRegistrator> registrators) {
    return new JacksonJsonMapperService(mapper, registrators);
  }
}
