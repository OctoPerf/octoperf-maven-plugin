package com.octoperf.entity.runtime;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link UserLoad}.
 * 
 * @author jerome
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class ScenarioJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final Scenario bean = ScenarioTest.newInstance();
    
    final String json = jsonService.toJson(bean);
    final Scenario fromJson = jsonService.fromJson(json, Scenario.class);
    assertEquals(bean, fromJson);
  }
}
