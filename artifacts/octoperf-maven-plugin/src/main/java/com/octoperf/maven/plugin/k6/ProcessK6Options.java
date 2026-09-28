package com.octoperf.maven.plugin.k6;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.ProcessBuilder.Redirect.to;
import static java.nio.charset.StandardCharsets.UTF_8;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * stdout carries the options, stderr K6's own log. Both go to files, so no pipe fills up and blocks k6, and the
 * timeout holds even when k6 hangs.
 */
@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class ProcessK6Options implements K6Options {
  private static final long TIMEOUT_SECONDS = 120;

  @NonNull
  ObjectMapper mapper;

  @Override
  public JsonNode inspect(final String executable, final File entrypoint) throws IOException {
    final File script = entrypoint.getAbsoluteFile();
    final Path stdout = Files.createTempFile("k6-inspect", ".json");
    final Path stderr = Files.createTempFile("k6-inspect", ".log");
    try {
      final Process process = new ProcessBuilder(
        List.of(executable, "inspect", "--execution-requirements", script.getName()))
        .directory(script.getParentFile())
        .redirectOutput(to(stdout.toFile()))
        .redirectError(to(stderr.toFile()))
        .start();
      checkExit(executable, process, stderr);
      return parsed(Files.readString(stdout, UTF_8), executable + " inspect");
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Interrupted while running " + executable + " inspect", e);
    } finally {
      deleteQuietly(stdout);
      deleteQuietly(stderr);
    }
  }

  private static void checkExit(final String executable, final Process process, final Path stderr)
    throws IOException, InterruptedException {
    if (!process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
      process.destroyForcibly().waitFor(5, TimeUnit.SECONDS);
      throw new IOException(executable + " inspect did not end within " + TIMEOUT_SECONDS + " seconds");
    }
    if (process.exitValue() != 0) {
      throw new IOException(executable + " inspect exited with " + process.exitValue() + ": "
        + Files.readString(stderr, UTF_8).strip());
    }
  }

  /**
   * A file k6 still holds cannot be deleted on Windows: the error that ended the run matters more than a leftover.
   */
  private static void deleteQuietly(final Path file) {
    try {
      Files.deleteIfExists(file);
    } catch (final IOException e) {
      file.toFile().deleteOnExit();
    }
  }

  @Override
  public JsonNode read(final File options) throws IOException {
    return parsed(Files.readString(options.toPath(), UTF_8), options.toString());
  }

  private JsonNode parsed(final String json, final String source) throws IOException {
    final JsonNode options = mapper.readTree(json);
    if (options != null && options.isObject()) {
      return options;
    }
    throw new IOException(source + " printed no JSON object: " + json.strip());
  }
}
