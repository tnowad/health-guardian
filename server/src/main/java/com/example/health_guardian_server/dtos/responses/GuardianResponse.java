package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuardianResponse {
  private String id;
  private String name;
  private String relationshipToPatient;
  private String phone;
  private String email;
}
