package com.octoperf.tools.retrofit;

import com.google.common.testing.NullPointerTester;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertNotNull;

public class RetrofitWrapperTest {

  Retrofit retrofit;
  RetrofitWrapper wrapper;

  @BeforeEach
  public void before(){
    retrofit = new Retrofit.Builder().baseUrl("http://localhost").build();
    wrapper = new RetrofitWrapper(retrofit);
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(RetrofitWrapper.class, PACKAGE);
  }

  @Test
  public void shouldCreateRestApi(){
    assertNotNull(wrapper.create(TestApi.class));
  }
}
