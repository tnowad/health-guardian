package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

@Data
public class CreateHouseholdMemberRequest {

  private String householdId;
  private String patientId;
  private String relationshipToPatient;
}
