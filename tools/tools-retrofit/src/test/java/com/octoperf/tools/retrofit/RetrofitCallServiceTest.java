package com.octoperf.tools.retrofit;

import okhttp3.HttpUrl;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
public class RetrofitCallServiceTest {

  @Mock
  Call<Object> call;
  @Mock
  Callback<Object> callback;
  @Mock
  Request request;

  private Response<Object> response;
  private CallService service;

  @BeforeEach
  public void before() {
    service = new RetrofitCallService();
    when(call.request()).thenReturn(request);
    when(request.url()).thenReturn(HttpUrl.get("https://octoperf.com"));
  }

  @Test
  public void shouldExecuteFineWithNull() throws IOException {
    response = Response.success(null);
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call);
    Assertions.assertFalse(execute.isPresent());
  }

  @Test
  public void shouldExecuteFine() throws IOException {
    response = Response.success(new Object());
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call);
    Assertions.assertTrue(execute.isPresent());
  }

  @Test
  public void shouldExecuteFineCallback() throws IOException {
    response = Response.success(new Object());
    when(call.execute()).thenReturn(response);
    final Optional<Object> execute = service.execute(call, callback);
    Assertions.assertTrue(execute.isPresent());
    verify(callback).onResponse(call, response);
  }

  @Test
  public void shouldExecuteWithError() throws IOException {
    when(call.execute()).thenThrow(new IOException());
    final Optional<Object> execute = service.execute(call);
    Assertions.assertFalse(execute.isPresent());
  }

  @Test
  public void shouldExecuteWithErrorCallback() throws IOException {
    final IOException e = new IOException();
    when(call.execute()).thenThrow(e);
    final Optional<Object> execute = service.execute(call, callback);
    Assertions.assertFalse(execute.isPresent());
    verify(callback).onFailure(call, e);
  }
}
