package com.example.health_guardian_server.dtos.responses.diagnostic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticReportResponse {
  private String id;
  private String userId;
  private Date reportDate;
  private String reportType;
  private String summary;
  private String notes;
}
