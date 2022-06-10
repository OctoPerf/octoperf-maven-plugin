package com.octoperf.entity.design;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class HttpServerJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final HttpServer dto = HttpServerTest.newInstance();
    
    final String json = jsonService.toJson(dto);
    final HttpServer fromJson = jsonService.fromJson(json, HttpServer.class);
    assertEquals(dto, fromJson);
  }
}
