package com.octoperf.maven.rest;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octoperf.entity.runtime.k6.K6ScenarioConversion;
import com.octoperf.entity.runtime.k6.K6ScenarioRequest;
import com.octoperf.runtime.rest.api.K6ScenarioApi;
import com.octoperf.tools.retrofit.CallService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestK6ScenariosTest {

  @Mock
  K6ScenarioApi api;
  @Mock
  CallService calls;
  @Mock
  Call<K6ScenarioConversion> call;

  @Test
  void shouldHandTheConversionTheServerAnswered() throws IOException {
    final K6ScenarioRequest request = new K6ScenarioRequest("vu", "main.js", "provider", "eu-west-1", "Scenario",
      JsonNodeFactory.instance.objectNode());
    final K6ScenarioConversion conversion = new K6ScenarioConversion(null, List.of());
    when(api.create("p", request)).thenReturn(call);
    when(calls.executeOrThrow(call)).thenReturn(conversion);
    assertSame(conversion, new RestK6Scenarios(api, calls).create("p", request));
  }
}
