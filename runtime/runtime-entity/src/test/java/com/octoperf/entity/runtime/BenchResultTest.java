package com.octoperf.entity.runtime;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static com.octoperf.entity.runtime.BenchResultState.PENDING;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link BenchResult}.
 *
 * @author jerome
 *
 */
public class BenchResultTest {

  @Test
  public void shouldPassNPE() {
    final BenchResult entity = create();
    new NullPointerTester()
      .testConstructors(entity.getClass(), PACKAGE);
  }

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(BenchResult.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(BenchResult.class, PACKAGE);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(create());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(create().toString().contains("@"));
  }

  public BenchResult create() {
    return newInstance();
  }

  public static BenchResult newInstance() {
    return builder().build();
  }

  public static BenchResult.BenchResultBuilder builder() {
    return BenchResult.builder()
      .id("benchResultId")
      .batchId("batchId")
      .scenarioId("scenarioId")
      .designProjectId("designProjectId")
      .resultProjectId("resultsProjectId")
      .state(PENDING);
  }
}
