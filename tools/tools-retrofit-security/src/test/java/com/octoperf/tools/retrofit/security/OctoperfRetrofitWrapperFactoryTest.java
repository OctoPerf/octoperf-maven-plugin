package com.octoperf.tools.retrofit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.NullPointerTester;
import com.octoperf.tools.retrofit.RestApiWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OctoperfRetrofitWrapperFactoryTest {

  @Mock
  ObjectMapper objectMapper;
  @Mock
  X509TrustManager trustManager;
  @Mock
  SSLSocketFactory socketFactory;

  @Mock
  RestApiWrapper unsecuredWrapper;

  OctoperfRetrofitWrapperFactory wrapperFactory;

  @Before
  public void before() {
    when(trustManager.getAcceptedIssuers()).thenReturn(new X509Certificate[0]);
    wrapperFactory = new OctoperfRetrofitWrapperFactory(objectMapper, trustManager, socketFactory);
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester()
      .setDefault(SSLSocketFactory.class, (SSLSocketFactory)SSLSocketFactory.getDefault())
      .testConstructors(wrapperFactory.getClass(), PACKAGE);
  }

  @Test
  public void shouldCreateWrapper() {
    assertNotNull(wrapperFactory.newWrapper("http://localhost", "apiKey"));
  }
}
