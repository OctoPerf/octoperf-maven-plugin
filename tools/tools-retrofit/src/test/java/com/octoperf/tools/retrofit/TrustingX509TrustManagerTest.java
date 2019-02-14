package com.octoperf.tools.retrofit;

import org.junit.Test;

public class TrustingX509TrustManagerTest {

  @Test
  public void shouldCheck() {
    final TrustingX509TrustManager trustManager = new TrustingX509TrustManager();
    trustManager.getAcceptedIssuers();
    trustManager.checkClientTrusted(null, null);
    trustManager.checkServerTrusted(null, null);
  }
}
