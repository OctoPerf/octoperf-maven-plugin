package com.octoperf.entity.design;

import com.google.common.testing.NullPointerTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.google.common.testing.NullPointerTester.Visibility.PUBLIC;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class HttpServerTest {
  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(HttpServer.class).verify();
  }

  @Test
  public void shouldPassNullPointerTester() {
    new NullPointerTester().testConstructors(HttpServer.class, PUBLIC);
  }

  @Test
  public void shouldCreate() {
    assertNotNull(newInstance());
  }

  @Test
  public void shouldHaveNonStandardToString() {
    assertFalse(newInstance().toString().contains("@"));
  }

  public static HttpServer newInstance() {
    return builder().build();
  }

  public static HttpServer.HttpServerBuilder builder() {
    return HttpServer.builder()
      .id("serverId")
      .projectId("projectId");
  }
}
