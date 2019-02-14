package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link VirtualUserDescription}.
 *
 * @author jerome
 *
 */
public class VirtualUserDescriptionTest {

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(VirtualUserDescription.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(VirtualUserDescription.class, PACKAGE);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static VirtualUserDescription newInstance() {
    return VirtualUserDescription.builder()
      .id("id")
      .name("name")
      .description("description")
      .projectId("projectId")
      .build();
  }
}
