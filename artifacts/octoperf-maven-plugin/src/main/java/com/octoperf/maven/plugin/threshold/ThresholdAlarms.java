package com.octoperf.maven.plugin.threshold;

import java.io.IOException;

public interface ThresholdAlarms {

  boolean hasAlarms(
    String benchResultId,
    ThresholdSeverity severity) throws IOException;
}
