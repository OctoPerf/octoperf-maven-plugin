package com.octoperf.maven.plugin.threshold;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.String.format;
import static lombok.AccessLevel.PACKAGE;
import static okhttp3.MediaType.parse;
import static okhttp3.RequestBody.create;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class SpringThresholdAlarms implements ThresholdAlarms {
  private static final String THRESHOLD_REPORT_ITEM =
    "{\"@type\":\"ThresholdAlarmReportItem\",\"metric\":{\"@type\":\"MonitoringMetric\",\"benchResultId\":\"%s\", \"filters\":[],\"id\":\"\",\"type\":\"NUMBER_COUNTER\"},\"name\":\"\"}";

  @NonNull
  ThresholdAlarmApi api;

  public boolean hasAlarms(
    final String benchResultId,
    final ThresholdSeverity severity) throws IOException {

    return api
      .getAlarms(create(format(THRESHOLD_REPORT_ITEM, benchResultId), parse("application/json; charset=utf-8")))
      .execute()
      .body()
      .stream()
      .map(ThresholdAlarm::getSeverity)
      .anyMatch(s -> s.ordinal() >= severity.ordinal());
  }
}
