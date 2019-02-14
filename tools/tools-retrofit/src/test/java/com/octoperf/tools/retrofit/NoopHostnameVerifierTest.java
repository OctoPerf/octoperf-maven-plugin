package com.octoperf.tools.retrofit;

import org.junit.Test;

import static com.octoperf.tools.retrofit.NoopHostnameVerifier.INSTANCE;
import static org.junit.Assert.assertTrue;

public class NoopHostnameVerifierTest {

  @Test
  public void shouldNotVerify() {
    assertTrue(INSTANCE.verify("", null));
  }
}
