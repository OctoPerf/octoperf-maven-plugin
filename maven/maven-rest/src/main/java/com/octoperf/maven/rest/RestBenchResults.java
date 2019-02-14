package com.octoperf.maven.rest;

import com.octoperf.commons.entity.ValueWrapper;
import com.octoperf.entity.runtime.BenchResult;
import com.octoperf.maven.api.BenchResults;
import com.octoperf.runtime.rest.api.BenchResultsApi;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestBenchResults implements BenchResults {
  @NonNull
  BenchResultsApi api;
  @NonNull
  CallService calls;

  @Override
  public BenchResult find(final String id) throws IOException {
    return calls
      .execute(api.find(id))
      .orElseThrow(() -> new IOException("Could not find benchResultId=" + id));
  }

  @Override
  public void stopTest(final String benchResultId) {
    calls.execute(api.stop(benchResultId));
  }

  @Override
  public double getProgress(final String id) {
    return calls
      .execute(api.getProgress(id))
      .map(ValueWrapper::getValue)
      .orElse(0d);
  }
}
