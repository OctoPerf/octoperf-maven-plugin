package com.octoperf.entity.runtime.k6;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.octoperf.entity.runtime.BenchResult;
import com.octoperf.entity.runtime.BenchResultState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Reads what the server writes, with the mapper the plugin configures.
 */
class K6ScenarioJsonTest {
  static final ObjectMapper MAPPER = JsonMapper
    .builder()
    .findAndAddModules()
    .serializationInclusion(NON_NULL)
    .build();

  @Test
  void shouldReadTheCreatedScenarioAndWhatDidNotConvert() throws IOException {
    final K6ScenarioConversion conversion = MAPPER.readValue("""
      {"scenario": {"id": "s1", "projectId": "p1", "name": "Scenario", "description": "", "userProfiles": []},
       "unconverted": [{"scenario": "arrivals", "kind": "OPEN_MODEL", "outcome": "NOT_CONVERTED"},
                       {"scenario": "x", "kind": "A_NEWER_KIND", "outcome": "A_NEWER_OUTCOME"}]}
      """, K6ScenarioConversion.class);
    assertEquals("s1", conversion.getScenario().orElseThrow().getId());
    assertEquals(List.of(
      new K6Unconverted("arrivals", K6UnconvertedKind.OPEN_MODEL, K6UnconvertedOutcome.NOT_CONVERTED),
      new K6Unconverted("x", K6UnconvertedKind.UNKNOWN, K6UnconvertedOutcome.UNKNOWN)), conversion.getUnconverted());
  }

  @Test
  void shouldReadNoScenarioWhenNothingConverted() throws IOException {
    final K6ScenarioConversion conversion = MAPPER.readValue("""
      {"scenario": null, "unconverted": [{"scenario": "", "kind": "NO_SCENARIOS"}]}
      """, K6ScenarioConversion.class);
    assertTrue(conversion.getScenario().isEmpty());
    assertEquals("", conversion.getUnconverted().get(0).getScenario());
  }

  @Test
  void shouldSendTheOptionsUntouchedAndLeaveAMissingNameOut() throws IOException {
    final JsonNode options = MAPPER.readTree("{\"scenarios\": {\"default\": {\"executor\": \"constant-vus\"}}}");
    final JsonNode sent = MAPPER.readTree(MAPPER.writeValueAsString(
      new K6ScenarioRequest("vu", "main.js", "provider", "eu-west-1", null, options)));
    assertEquals(options, sent.get("options"));
    assertFalse(sent.has("name"));
    assertEquals("vu", sent.get("virtualUserId").asText());
    assertEquals("main.js", sent.get("entrypointFileName").asText());
  }

  @Test
  void shouldReadAStateANewerServerReports() throws IOException {
    final BenchResult result = MAPPER.readValue("""
      {"id": "b", "batchId": "b", "scenarioId": "s", "designProjectId": "d", "resultProjectId": "r",
       "state": "SUSPENDED"}
      """, BenchResult.class);
    assertEquals(BenchResultState.UNKNOWN, result.getState());
  }
}
