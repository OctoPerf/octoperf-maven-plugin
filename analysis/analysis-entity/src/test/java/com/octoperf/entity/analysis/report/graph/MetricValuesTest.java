package com.octoperf.entity.analysis.report.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class MetricValuesTest {

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(MetricValues.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(MetricValues.class, Visibility.PACKAGE);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static MetricValues newInstance() {
    return new MetricValues(ImmutableList.of(MetricValueTest.newInstance()));
  }
}
