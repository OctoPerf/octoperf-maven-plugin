package com.octoperf.analysis.rest.client;

import com.octoperf.entity.analysis.report.BenchReportTemplate;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * {@link BenchReportTemplate} REST Service.
 *
 * @author jerome
 *
 */
public interface BenchReportTemplatesApi {

  @GET("/analysis/bench-report-templates/by-workspace/{workspaceId}")
  Call<List<BenchReportTemplate>> find(@Path("workspaceId") String workspaceId);
}
