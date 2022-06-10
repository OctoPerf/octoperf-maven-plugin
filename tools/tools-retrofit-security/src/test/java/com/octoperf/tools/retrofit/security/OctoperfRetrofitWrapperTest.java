package com.octoperf.tools.retrofit.security;

import com.google.common.testing.NullPointerTester;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import retrofit2.Retrofit;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
public class OctoperfRetrofitWrapperTest {

  @Mock
  RestClientAuthenticator authenticator;

  Retrofit retrofit;
  OctoperfRetrofitWrapper wrapper;

  @Before
  public void before(){
    retrofit = new Retrofit.Builder().baseUrl("http://localhost").build();
    wrapper = new OctoperfRetrofitWrapper(retrofit, authenticator);
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester()
      .setDefault(Retrofit.class, retrofit)
      .testConstructors(wrapper.getClass(), PACKAGE);
  }

  @Test
  public void shouldCreateRestApi(){
    assertNotNull(wrapper.create(TestApi.class));
  }

  @Test
  public void shouldReturnAuthenticator(){
    assertNotNull(wrapper.authenticator());
  }
}
