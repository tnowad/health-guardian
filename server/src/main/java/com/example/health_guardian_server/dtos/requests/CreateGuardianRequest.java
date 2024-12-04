package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

@Data
public class CreateGuardianRequest {
  private String name;
  private String relationshipToPatient;
  private String phone;
  private String email;
}
