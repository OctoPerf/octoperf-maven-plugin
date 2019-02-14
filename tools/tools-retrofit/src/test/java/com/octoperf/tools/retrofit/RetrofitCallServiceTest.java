package com.octoperf.tools.retrofit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RetrofitCallServiceTest {

  @Mock
  Call<Object> call;

  private Response<Object> response;
  private CallService service;

  @Before
  public void before() throws IOException {
    service = new RetrofitCallService();
  }

  @Test
  public void shouldExecuteFineWithNull() throws IOException {
    response = Response.success(null);
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call);
    assertFalse(execute.isPresent());
  }

  @Test
  public void shouldExecuteFine() throws IOException {
    response = Response.success(new Object());
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call);
    assertTrue(execute.isPresent());
  }

  @Test
  public void shouldExecuteWithError() throws IOException {
    when(call.execute()).thenThrow(new IOException());
    final Optional<Object> execute = service.execute(call);
    assertFalse(execute.isPresent());
  }
}
