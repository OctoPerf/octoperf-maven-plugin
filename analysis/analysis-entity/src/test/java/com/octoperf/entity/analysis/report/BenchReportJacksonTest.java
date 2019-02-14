package com.octoperf.entity.analysis.report;

import com.octoperf.tools.jackson.mapper.JacksonConfig;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JacksonConfig.class})
public class BenchReportJacksonTest {

  @Autowired
  private JsonMapperService jsonService;

  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final BenchReport bean = BenchReportTest.newInstance();

    final String json = jsonService.toJson(bean);
    final BenchReport fromJson = jsonService.fromJson(json, BenchReport.class);
    assertEquals(bean, fromJson);
  }
}
