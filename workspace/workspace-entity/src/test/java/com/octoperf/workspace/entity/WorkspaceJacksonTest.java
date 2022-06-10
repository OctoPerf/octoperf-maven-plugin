package com.octoperf.workspace.entity;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= Application.class)
public class WorkspaceJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final Workspace dto = WorkspaceTest.newInstance();
    
    final String json = jsonService.toJson(dto);
    final Workspace fromJson = jsonService.fromJson(json, Workspace.class);
    assertEquals(dto, fromJson);
  }
}
