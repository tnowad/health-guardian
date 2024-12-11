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
public class DiagnosticResultResponse {
  private String id;
  private String userId;
  private String testName;
  private Date resultDate;
  private String resultValue;
  private String notes;
}
