package com.octoperf.tools.retrofit;

import com.octoperf.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RetrofitConfigTest {

  @Autowired
  RestApiWrapperFactory factory;

  @Test
  public void shouldCheck() {
    assertNotNull(factory);
  }
}
