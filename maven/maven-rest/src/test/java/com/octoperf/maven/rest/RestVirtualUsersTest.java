package com.octoperf.maven.rest;

import com.octoperf.design.rest.api.ImportApi;
import com.octoperf.design.rest.api.VirtualUserApi;
import com.octoperf.entity.design.VirtualUser;
import com.octoperf.tools.retrofit.CallService;
import okhttp3.MultipartBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import retrofit2.Call;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class RestVirtualUsersTest {
  static final VirtualUser USER = VirtualUser.builder().id("vu").projectId("p").name("main.js").description("").build();

  @Mock
  VirtualUserApi vus;
  @Mock
  ImportApi imports;
  @Mock
  CallService calls;
  @Mock
  Call<VirtualUser> importCall;
  @TempDir
  Path folder;

  RestVirtualUsers virtualUsers;

  @BeforeEach
  void setup() throws IOException {
    when(imports.importK6(any(), anyList())).thenReturn(importCall);
    when(calls.executeOrThrow(importCall)).thenReturn(USER);
    virtualUsers = new RestVirtualUsers(vus, imports, calls);
  }

  @Test
  void shouldSendTheEntrypointFirstAndEachModuleUnderItsImportPath() throws IOException {
    final File entrypoint = Files.writeString(folder.resolve("main.js"), "export default function () {}").toFile();
    Files.createDirectories(folder.resolve("lib"));
    final File module = Files.writeString(folder.resolve("lib/api.js"), "export const x = 1;").toFile();

    assertEquals(USER, virtualUsers.importK6("p", entrypoint, List.of(module)));

    @SuppressWarnings("unchecked")
    final ArgumentCaptor<List<MultipartBody.Part>> parts = ArgumentCaptor.forClass(List.class);
    verify(imports).importK6(eq("p"), parts.capture());
    assertEquals(
      List.of("form-data; name=\"files\"; filename=\"main.js\"", "form-data; name=\"files\"; filename=\"lib/api.js\""),
      parts.getValue().stream().map(part -> part.headers().get("Content-Disposition")).toList());
  }

  @Test
  void shouldRefuseAModuleOutsideTheEntrypointFolder() throws IOException {
    Files.createDirectories(folder.resolve("k6"));
    final File entrypoint = Files.writeString(folder.resolve("k6/main.js"), "").toFile();
    final File module = Files.writeString(folder.resolve("shared.js"), "").toFile();
    final IOException error = assertThrows(IOException.class,
      () -> virtualUsers.importK6("p", entrypoint, List.of(module)));
    assertTrue(error.getMessage().contains("is not under the entrypoint's folder"));
  }
}
