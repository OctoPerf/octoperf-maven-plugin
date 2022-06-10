package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import org.junit.jupiter.api.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static nl.jqno.equalsverifier.EqualsVerifier.forClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class VariableTest {

  @Test
  public void shouldPassEqualsVerifier() {
    forClass(Variable.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(Variable.class, PACKAGE);
  }
  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static Variable newInstance() {
    return new Variable("id");
  }
}
