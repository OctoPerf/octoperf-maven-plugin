package com.octoperf.entity.runtime;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link UserLoad}.
 * 
 * @author jerome
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BenchResultJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final BenchResult bean = BenchResultTest.newInstance();
    
    final String json = jsonService.toJson(bean);
    final BenchResult fromJson = jsonService.fromJson(json, BenchResult.class);
    assertEquals(bean, fromJson);
  }
}
