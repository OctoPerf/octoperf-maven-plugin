package com.octoperf.maven.api;

import java.io.IOException;

@FunctionalInterface
public interface Tasks {

  void generatePdfReport(String reportId) throws InterruptedException, IOException;
}
