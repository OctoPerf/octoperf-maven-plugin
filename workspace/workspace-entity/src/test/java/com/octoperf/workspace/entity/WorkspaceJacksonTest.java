package com.octoperf.workspace.entity;

import com.octoperf.Application;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link Workspace}.
 * 
 * @author jerome
 *
 */
@RunWith(SpringRunner.class)
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
