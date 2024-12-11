package com.example.health_guardian_server.dtos.requests.diagnostic;

import lombok.Data;

import java.util.Date;
@Data
public class CreateDiagnosticResultRequest {
  private String userId;
  private String testName;
  private Date resultDate;
  private String resultValue;
  private String notes;
}
