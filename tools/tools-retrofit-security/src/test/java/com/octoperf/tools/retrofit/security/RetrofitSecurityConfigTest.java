package com.octoperf.tools.retrofit.security;

import com.octoperf.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class RetrofitSecurityConfigTest {

  @Autowired
  SecuredRestApiWrapperFactory factory;

  @Test
  public void shouldCheck() {
    assertNotNull(factory);
  }
}
