package com.octoperf.maven.rest;

import com.octoperf.analysis.rest.client.LogApi;
import com.octoperf.tools.retrofit.CallService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestBenchLogsTest {

  @Mock
  LogApi api;
  @Mock
  CallService calls;
  @TempDir
  Path folder;

  @Test
  void shouldDownloadNothingWhenNoOtherExtensionIsAsked() throws IOException {
    new RestBenchLogs(api, calls).downloadOtherFiles(folder.toFile(), "", "benchResultId");
    verify(api, never()).getFiles(anyString());
  }
}
