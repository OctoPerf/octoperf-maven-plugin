package com.octoperf.maven.rest;

import com.google.common.collect.ImmutableSet;
import com.octoperf.design.rest.api.ProjectFilesApi;
import com.octoperf.maven.api.ProjectFiles;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static okhttp3.MultipartBody.Part.createFormData;
import static okhttp3.RequestBody.create;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestProjectFiles implements ProjectFiles {
  @NonNull
  ProjectFilesApi api;
  @NonNull
  CallService calls;

  @Override
  public void removeAll(final String projectId) {
    calls
      .execute(api.listFiles(projectId))
      .orElse(ImmutableSet.of())
      .stream()
      .map(filename -> api.delete(projectId, filename))
      .forEach(calls::execute);
  }

  @Override
  public void uploadAll(
    final String projectId,
    final File folder) {
    final File[] files = firstNonNull(folder.listFiles(), new File[0]);
    for (final File file : files) {
      final RequestBody requestFile = create(MediaType.parse(PLAIN_TEXT_UTF_8.toString()), file);
      final String filename = file.getName();
      final MultipartBody.Part body = createFormData("file", filename, requestFile);

      calls.execute(api.upload(projectId, filename, body));
    }
  }
}
