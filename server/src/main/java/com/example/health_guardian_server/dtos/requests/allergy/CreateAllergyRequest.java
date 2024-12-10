package com.example.health_guardian_server.dtos.requests.allergy;

import lombok.Data;

@Data
public class CreateAllergyRequest {
  private String userId;
  private String allergyName;
  private String severity;
  private String reactionDescription;
}
