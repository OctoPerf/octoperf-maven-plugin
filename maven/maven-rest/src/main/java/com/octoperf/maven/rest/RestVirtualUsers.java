package com.octoperf.maven.rest;


import com.google.common.collect.ImmutableList;
import com.octoperf.design.rest.api.ImportApi;
import com.octoperf.design.rest.api.VirtualUserApi;
import com.octoperf.entity.design.VirtualUser;
import com.octoperf.entity.design.VirtualUserDescription;
import com.octoperf.maven.api.VirtualUsers;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.net.MediaType.JAVASCRIPT_UTF_8;
import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static okhttp3.MediaType.parse;
import static okhttp3.MultipartBody.Part.createFormData;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestVirtualUsers implements VirtualUsers {
  @NonNull
  VirtualUserApi vus;
  @NonNull
  ImportApi imports;
  @NonNull
  CallService calls;

  @Override
  public void removeAll(final String projectId) {
    calls
      .execute(vus.listDescriptions(projectId))
      .orElse(ImmutableList.of())
      .stream()
      .map(VirtualUserDescription::getId)
      .map(vus::delete)
      .forEach(calls::execute);
  }

  @Override
  public void importJMX(
    final String projectId,
    final File file) throws IOException {
    final RequestBody requestFile = RequestBody.create(file, parse(PLAIN_TEXT_UTF_8.toString()));
    final MultipartBody.Part body = createFormData("file", file.getName(), requestFile);

    calls.executeOrThrow(imports.importJMX(projectId, body));
  }

  @Override
  public VirtualUser importK6(
    final String projectId,
    final File entrypoint,
    final List<File> modules) throws IOException {
    final Path folder = entrypoint
      .getAbsoluteFile()
      .getParentFile()
      .toPath()
      .normalize();
    final ImmutableList.Builder<MultipartBody.Part> parts = ImmutableList.builder();
    for (final File script : Stream.concat(Stream.of(entrypoint), modules.stream()).toList()) {
      parts.add(scriptPart(folder, script));
    }
    return calls.executeOrThrow(imports.importK6(projectId, parts.build()));
  }

  /**
   * The scripts import each other by relative path, so every module sits under the entrypoint's folder, which the
   * server checks too: a module elsewhere has no name to be imported by.
   */
  private static MultipartBody.Part scriptPart(final Path folder, final File script) throws IOException {
    final Path path = script
      .getAbsoluteFile()
      .toPath()
      .normalize();
    if (path.startsWith(folder)) {
      final String name = folder
        .relativize(path)
        .toString()
        .replace(File.separatorChar, '/');
      return createFormData("files", name, RequestBody.create(script, parse(JAVASCRIPT_UTF_8.toString())));
    }
    throw new IOException("K6 module " + script + " is not under the entrypoint's folder " + folder);
  }
}
