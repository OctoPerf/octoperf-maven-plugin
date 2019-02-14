package com.octoperf.entity.runtime;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link Scenario}.
 *
 * @author jerome
 *
 */
public class ScenarioTest {

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(Scenario.class).verify();
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static Scenario newInstance() {
    return Scenario.builder()
      .id("scenarioId")
      .name("scenario")
      .description("description")
      .projectId("projectId")
      .build();
  }
}
