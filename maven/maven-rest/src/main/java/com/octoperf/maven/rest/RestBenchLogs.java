package com.octoperf.maven.rest;

import com.google.common.base.Splitter;
import com.google.common.io.Closer;
import com.octoperf.analysis.rest.client.LogApi;
import com.octoperf.maven.api.BenchLogs;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.io.FileUtils.cleanDirectory;

@Slf4j
@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestBenchLogs implements BenchLogs {
  private static final String LOG_EXT = ".log";
  private static final String JTL_EXT = ".jtl";
  private static final String PDF_EXT = ".pdf";
  private static final String LOGS_FOLDER = "logs";
  private static final String JTLS_FOLDER = "jtls";
  private static final String OTHER_FOLDER = "other";
  public static final Splitter COMA_SPLITTER = Splitter.on(',').trimResults();

  @NonNull
  LogApi api;
  @NonNull
  CallService calls;

  @Override
  public void downloadOtherFiles(
    final File outputDir,
    final String extensions,
    final String benchResultId) throws IOException {
    downloadFiles(
      new File(outputDir, OTHER_FOLDER),
      COMA_SPLITTER.splitToList(extensions),
      benchResultId
    );
  }

  @Override
  public void downloadLogFiles(
    final File outputDir,
    final String benchResultId) throws IOException {
    downloadFiles(
      new File(outputDir, LOGS_FOLDER),
      List.of(LOG_EXT),
      benchResultId
    );
  }

  @Override
  public void downloadJtlFiles(
    final File outputDir,
    final String benchResultId) throws IOException {
    downloadFiles(
      new File(outputDir, JTLS_FOLDER),
      List.of(JTL_EXT),
      benchResultId
    );
  }

  @Override
  public void downloadPdfFiles(File outputDir, String benchResultId) throws IOException {
    downloadFiles(
      outputDir,
      List.of(PDF_EXT),
      benchResultId
    );
  }

  private void downloadFiles(
    final File folder,
    final List<String> extensions,
    final String benchResultId) throws IOException {
    log.info("Downloading " + extensions + " Files into '" + folder + "'...");
    final Set<String> files = api.getFiles(benchResultId).execute().body();
    log.info("Remote Files: " + files);

    folder.mkdirs();
    log.info("Cleaning folder: '" + folder + "'");
    cleanDirectory(folder);

    for(final String filename : files) {
      String outputFilename = filename.replace(".gz", "");

      final File logFile;
      if (extensions.stream().anyMatch(outputFilename::endsWith)) {
        logFile = new File(folder, outputFilename);
      } else {
        continue;
      }

      log.info("Downloading file: " + filename);
      final ResponseBody body = calls
        .execute(api.getFile(benchResultId, filename))
        .orElseThrow();

      final Closer closer = Closer.create();
      InputStream input = closer.register(new BufferedInputStream(body.byteStream()));
      try {
        final CompressorStreamFactory factory = new CompressorStreamFactory();
        input = factory.createCompressorInputStream(input);
      } catch (final CompressorException e) {
        // file is probably not compressed
        log.debug("Could not decompress filename=" + filename,e);
      }

      try {
        final OutputStream output = closer.register(newOutputStream(logFile.toPath(), CREATE, TRUNCATE_EXISTING));
        IOUtils.copy(input, output);
      } finally {
        closer.close();
      }
      log.info("Saved File: " + logFile);
    }
  }
}
