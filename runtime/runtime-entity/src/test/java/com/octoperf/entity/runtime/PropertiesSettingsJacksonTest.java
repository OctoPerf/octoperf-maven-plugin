package com.octoperf.entity.runtime;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class PropertiesSettingsJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final PropertiesSettings bean = PropertiesSettingsTest.newInstance();
    
    final String json = jsonService.toJson(bean);
    final PropertiesSettings fromJson = jsonService.fromJson(json, PropertiesSettings.class);
    assertEquals(bean, fromJson);
  }
}
