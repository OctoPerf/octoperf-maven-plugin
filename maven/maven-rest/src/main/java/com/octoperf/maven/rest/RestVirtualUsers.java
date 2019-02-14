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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static okhttp3.MultipartBody.Part.createFormData;
import static okhttp3.RequestBody.create;

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
    final File file) {
    final RequestBody requestFile = create(MediaType.parse(PLAIN_TEXT_UTF_8.toString()), file);
    final MultipartBody.Part body = createFormData("file", file.getName(), requestFile);

    calls.execute(imports.importJMX(projectId, body));
  }
}
