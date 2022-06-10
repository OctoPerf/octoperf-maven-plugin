package com.octoperf.entity.analysis.report.graph;

import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class MetricValueTest {

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(MetricValue.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(MetricValue.class, Visibility.PACKAGE);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static MetricValue newInstance() {
    return new MetricValue("name", 123d, "secs");
  }
}
