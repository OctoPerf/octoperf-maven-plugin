package com.octoperf.tools.retrofit.security;

import com.google.common.testing.NullPointerTester;
import okhttp3.Interceptor;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class APIKeyRestClientAuthenticatorTest {
  private APIKeyRestClientAuthenticator authenticator;

  @Mock
  private Interceptor.Chain chain;

  private Request request;

  @Captor
  ArgumentCaptor<Request> captor;

  @BeforeEach
  public void before() {
    authenticator = new APIKeyRestClientAuthenticator("apiKey");
    request = new Request.Builder().url("https://octoperf.com").build();
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(authenticator.getClass(), PACKAGE);
  }

  @Test
  public void shouldAuthenticate() throws IOException {
    when(chain.request()).thenReturn(request);
    authenticator.intercept(chain);
    verify(chain).proceed(captor.capture());
    assertEquals("Bearer apiKey", captor.getValue().header(AUTHORIZATION));
  }
}
