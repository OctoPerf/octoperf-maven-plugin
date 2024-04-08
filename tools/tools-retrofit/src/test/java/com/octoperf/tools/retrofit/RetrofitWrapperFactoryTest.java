package com.octoperf.tools.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.NullPointerTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
public class RetrofitWrapperFactoryTest {

  @Mock
  ObjectMapper objectMapper;
  @Mock
  X509TrustManager trustManager;
  @Mock
  SSLSocketFactory socketFactory;

  RetrofitWrapperFactory wrapperFactory;

  @BeforeEach
  public void before() {
    when(trustManager.getAcceptedIssuers()).thenReturn(new X509Certificate[0]);
    wrapperFactory = new RetrofitWrapperFactory(objectMapper, trustManager, socketFactory);
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester()
      .setDefault(SSLSocketFactory.class, (SSLSocketFactory) SSLSocketFactory.getDefault())
      .testConstructors(RetrofitWrapperFactory.class, PACKAGE);
  }

  @Test
  public void shouldCreateWrapper() {
    assertNotNull(wrapperFactory.newWrapper("http://localhost"));
  }
}
