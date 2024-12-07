package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentFormResponse {
  private String id;
  private String patientId;
  private String formName;
  private String consentDate;
  private String status;
}
