package com.octoperf.maven.rest;

import com.google.common.io.Closer;
import com.octoperf.analysis.rest.client.JUnitReportApi;
import com.octoperf.maven.api.JunitService;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.google.common.io.Closer.create;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestJUnits implements JunitService {
  private static final String JUNIT_REPORT_XML = "junit-report.xml";

  @NonNull
  JUnitReportApi api;
  @NonNull
  CallService calls;

  @Override
  public Path saveJUnitReport(
    final File folder,
    final String benchResultId) throws IOException {
    final Path path = folder.toPath().resolve(JUNIT_REPORT_XML);

    final ResponseBody body = calls
      .execute(api.getReport(benchResultId))
      .orElseThrow();
    final Closer closer = create();
    try {
      final InputStream input = closer.register(body.byteStream());
      final OutputStream output = closer.register(newOutputStream(path, CREATE, TRUNCATE_EXISTING));
      IOUtils.copy(input, output);
    } finally {
      closer.close();
    }
    return path;
  }
}
