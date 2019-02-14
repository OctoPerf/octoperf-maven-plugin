package com.octoperf.maven.api;

import com.octoperf.entity.analysis.report.graph.MetricValues;
import org.joda.time.DateTime;

public interface BenchMetrics {

  /**
   * Retrieves current test metrics from the server.
   * The returned metrics are test-wide metrics.
   * 
   * @param benchResultId bench result id
   * @return global test metrics.
   */
  MetricValues getMetrics(String benchResultId);
  
  /**
   * Converts the metrics into a human readable line of values
   * 
   * @param startTime test start time
   * @param metrics metrics to print
   * @return printable output
   */
  String toPrintable(DateTime startTime, MetricValues metrics);
  
}
