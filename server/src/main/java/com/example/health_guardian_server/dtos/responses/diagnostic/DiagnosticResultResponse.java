package com.example.health_guardian_server.dtos.responses.diagnostic;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private String file;
}
