package com.octoperf.maven.rest;

import com.google.common.collect.ImmutableList;
import com.octoperf.analysis.rest.client.MetricsApi;
import com.octoperf.entity.analysis.report.graph.MetricValue;
import com.octoperf.entity.analysis.report.graph.MetricValues;
import com.octoperf.maven.api.BenchMetrics;
import com.octoperf.tools.retrofit.CallService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class RestBenchMetrics implements BenchMetrics {
  @NonNull
  MetricsApi api;
  @NonNull
  CallService calls;

  @Override
  public MetricValues getMetrics(final String benchResultId) {
    return calls
      .execute(api.getGlobalMetrics(benchResultId))
      .orElse(new MetricValues(ImmutableList.of()));
  }

  @Override
  public String toPrintable(final DateTime startTime, final MetricValues metrics) {
    final DateTime now = DateTime.now();
    final Duration duration = new Duration(startTime, now);

    final StringBuilder b = new StringBuilder(256);
    for(final MetricValue metric : metrics.getMetrics()) {
      b
        .append(metric.getName())
        .append(": ")
        .append(String.format("%.2f",metric.getValue()))
        .append(metric.getUnit())
        .append(" ");
    }

    b.append("Duration: ").append(duration.getStandardSeconds() + "s");
    return b.toString();
  }

}
