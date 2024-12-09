package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

import java.util.Date;

@Data
public class CreateImmunizationRequest {
  private String userId;
  private Date vaccinationDate;
  private String vaccineName;
  private String batchNumber;
  private String notes;
}
