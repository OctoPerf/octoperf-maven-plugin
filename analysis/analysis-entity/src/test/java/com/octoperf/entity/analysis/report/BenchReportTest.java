package com.octoperf.entity.analysis.report;

import com.google.common.collect.ImmutableList;
import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link BenchReport}.
 *
 * @author jerome
 */
public class BenchReportTest {

  @Test
  public void shouldPassNPE() {
    final BenchReport entity = newInstance();
    new NullPointerTester()
      .testConstructors(entity.getClass(), PACKAGE);
  }

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(BenchReport.class).verify();
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static BenchReport newInstance() {
    return newInstance("benchReportId", "projectId");
  }

  public static BenchReport newInstance(final String id, final String projectId) {
    return builder()
      .id(id)
      .projectId(projectId)
      .build();
  }

  public static BenchReport.BenchReportBuilder builder() {
    return BenchReport.builder()
      .id("benchReportId")
      .projectId("projectId")
      .benchResultIds(ImmutableList.of("benchResultId"))
      .name("name")
      .description("description");
  }
}
