package com.octoperf.maven.api;

import java.io.File;
import java.io.IOException;

public interface BenchLogs {

  void downloadLogFiles(
    File outputDir,
    String benchResultId) throws IOException;

  void downloadJtlFiles(
    File outputDir,
    String benchResultId) throws IOException;

  void downloadPdfFiles(
    File outputDir,
    String benchResultId) throws IOException;
}
