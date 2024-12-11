package com.example.health_guardian_server.dtos.requests.diagnostic;

import lombok.Data;

import java.util.Date;

@Data
public class CreateDiagnosticReportRequest {
  private String userId;
  private Date reportDate;
  private String reportType;
  private String summary;
  private String note;
}
