package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ProjectTest {

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(Project.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(Project.class, PACKAGE);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static Project newInstance() {
    return Project
      .builder()
      .workspaceId("workspaceId")
      .id("projectId")
      .name("name")
      .description("description")
      .build();
  }

}
