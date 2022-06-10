package com.octoperf.entity.analysis.report.graph;

import com.octoperf.tools.jackson.mapper.JacksonConfig;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link MetricValue}.
 * 
 * @author jerome
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JacksonConfig.class})
public class MetricValueJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final MetricValue bean = MetricValueTest.newInstance();
    
    final String json = jsonService.toJson(bean);
    final MetricValue fromJson = jsonService.fromJson(json, MetricValue.class);
    assertEquals(bean, fromJson);
  }
}
