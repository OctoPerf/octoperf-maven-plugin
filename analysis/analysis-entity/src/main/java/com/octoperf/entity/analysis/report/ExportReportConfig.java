package com.octoperf.entity.analysis.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class ExportReportConfig implements BenchReportConfig {

  String orientation;
  String coverPage;
  Map<String, Integer> tablesRowCount;

  @JsonCreator
  ExportReportConfig(
    @JsonProperty("orientation") final String orientation,
    @JsonProperty("coverPage") final String coverPage,
    @JsonProperty("tablesRowCount") final Map<String, Integer> tablesRowCount) {
    super();
    this.orientation = requireNonNull(orientation);
    this.coverPage = requireNonNull(coverPage);
    this.tablesRowCount = requireNonNull(tablesRowCount);
  }
}
