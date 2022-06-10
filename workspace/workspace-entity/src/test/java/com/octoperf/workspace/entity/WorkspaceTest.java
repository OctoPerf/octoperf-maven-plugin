package com.octoperf.workspace.entity;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class WorkspaceTest {

  @Test
  public void shouldPassNPE() {
    final Workspace entity = create();
    new NullPointerTester()
      .setDefault(Optional.class, Optional.empty())
      .testConstructors(entity.getClass(), PACKAGE);
  }

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(Workspace.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(Workspace.class, PACKAGE);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(create());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(create().toString().contains("@"));
  }

  protected Workspace create() {
    return newInstance();
  }

  public static Workspace newInstance() {
    return Workspace
      .builder()
      .id("workspaceId")
      .description("")
      .name("")
      .build();
  }
}
