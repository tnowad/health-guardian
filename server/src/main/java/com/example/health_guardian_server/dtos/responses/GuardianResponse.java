package com.example.health_guardian_server.dtos.responses;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GuardianResponse {
  private String name;

  private String relationshipToPatient;

  private String phone;

  private String email;
}
