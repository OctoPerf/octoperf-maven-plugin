package com.octoperf.maven.rest;

import com.fasterxml.jackson.databind.node.NullNode;
import com.octoperf.tools.retrofit.CallService;
import com.octoperf.workspace.entity.DockerProvider;
import com.octoperf.workspace.rest.api.DockerProvidersApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class RestDockerProvidersTest {
  static final DockerProvider OWN = new DockerProvider("own", "On Premise", true, Map.of("lan", NullNode.getInstance()));
  static final DockerProvider DISABLED = new DockerProvider("off", "OctoPerf", false, Map.of("eu-west-1", NullNode.getInstance()));
  static final DockerProvider SAAS = new DockerProvider("saas", "OctoPerf", null, Map.of("eu-west-1", NullNode.getInstance()));

  @Mock
  DockerProvidersApi api;
  @Mock
  CallService calls;
  @Mock
  Call<List<DockerProvider>> workspaceCall;
  @Mock
  Call<List<DockerProvider>> publicCall;

  RestDockerProviders providers;

  @BeforeEach
  void setup() throws IOException {
    when(api.byWorkspace("ws")).thenReturn(workspaceCall);
    when(api.publicProviders()).thenReturn(publicCall);
    when(calls.executeOrThrow(workspaceCall)).thenReturn(List.of(OWN, DISABLED));
    when(calls.executeOrThrow(publicCall)).thenReturn(List.of(SAAS));
    providers = new RestDockerProviders(api, calls);
  }

  @Test
  void shouldFindAPublicProviderByName() throws IOException {
    assertEquals("saas", providers.getProviderId("ws", "OctoPerf", "eu-west-1"));
  }

  @Test
  void shouldFindAWorkspaceProviderByName() throws IOException {
    assertEquals("own", providers.getProviderId("ws", "On Premise", "lan"));
  }

  @Test
  void shouldNameTheAvailableProvidersOfAnUnknownOne() {
    final IOException error = assertThrows(IOException.class, () -> providers.getProviderId("ws", "Mars", "lan"));
    assertTrue(error.getMessage().endsWith("Available: OctoPerf, On Premise"));
  }

  @Test
  void shouldNameTheLocationsOfAProviderWithoutTheRequestedOne() {
    final IOException error = assertThrows(IOException.class,
      () -> providers.getProviderId("ws", "OctoPerf", "mars"));
    assertTrue(error.getMessage().endsWith("Its locations: eu-west-1"));
  }

  @Test
  void shouldSkipADisabledProviderOfTheSameName() throws IOException {
    assertEquals("saas", providers.getProviderId("ws", "OctoPerf", "eu-west-1"));
  }
}
