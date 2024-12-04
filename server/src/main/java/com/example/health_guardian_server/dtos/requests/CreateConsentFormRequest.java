package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

@Data
public class CreateConsentFormRequest {
  private String patientId;
  private String formName;
  private String consentDate;
  private String status;
}
