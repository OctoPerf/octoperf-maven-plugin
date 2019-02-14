package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import com.octoperf.entity.design.VirtualUser.VirtualUserBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link VirtualUser}.
 *
 * @author jerome
 */
public class VirtualUserTest {

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(VirtualUser.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(VirtualUser.class, PACKAGE);
  }
  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static VirtualUser newInstance() {
    return builder().build();
  }

  public static VirtualUserBuilder builder() {
    return VirtualUser
      .builder()
      .projectId("projectId")
      .id("virtualUserId")
      .name("name")
      .description("description");
  }

}
