package com.octoperf.maven.plugin.k6;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Stands in for k6 with shell scripts, so the test runs without it and pins what the goal hands the server.
 */
class ProcessK6OptionsTest {
  final ProcessK6Options options = new ProcessK6Options(JsonMapper.builder().build());

  @TempDir
  Path folder;

  @Test
  void shouldRunInspectWithTheRequirementsFromTheScriptFolder() throws IOException {
    final File entrypoint = Files.writeString(folder.resolve("main.js"), "").toFile();
    final String k6 = executable("fake-k6", """
      #!/bin/sh
      echo 'level=warning msg="unknown field"' >&2
      printf '{"args": "%s", "pwd": "%s"}' "$*" "$(pwd)"
      """);
    final JsonNode inspected = options.inspect(k6, entrypoint);
    assertEquals("inspect --execution-requirements main.js", inspected.get("args").asText());
    assertEquals(folder.toRealPath().toString(), inspected.get("pwd").asText());
  }

  @Test
  void shouldFailWithWhatK6Said() throws IOException {
    final File entrypoint = Files.writeString(folder.resolve("main.js"), "").toFile();
    final String k6 = executable("failing-k6", """
      #!/bin/sh
      echo 'SyntaxError: main.js:3:7' >&2
      exit 107
      """);
    final IOException error = assertThrows(IOException.class, () -> options.inspect(k6, entrypoint));
    assertTrue(error.getMessage().endsWith("inspect exited with 107: SyntaxError: main.js:3:7"));
  }

  @Test
  void shouldFailWhenK6PrintsNoObject() throws IOException {
    final File entrypoint = Files.writeString(folder.resolve("main.js"), "").toFile();
    final String k6 = executable("silent-k6", """
      #!/bin/sh
      echo '[]'
      """);
    assertThrows(IOException.class, () -> options.inspect(k6, entrypoint));
  }

  @Test
  void shouldFailWhenK6CannotStart() throws IOException {
    final File entrypoint = Files.writeString(folder.resolve("main.js"), "").toFile();
    assertThrows(IOException.class, () -> options.inspect(folder.resolve("missing-k6").toString(), entrypoint));
  }

  @Test
  void shouldReadOptionsInspectedEarlier() throws IOException {
    final File file = Files.writeString(folder.resolve("options.json"), "{\"scenarios\": {}}").toFile();
    assertTrue(options.read(file).get("scenarios").isObject());
  }

  private String executable(final String name, final String content) throws IOException {
    final File script = Files.writeString(folder.resolve(name), content).toFile();
    assertTrue(script.setExecutable(true));
    return script.getAbsolutePath();
  }
}
