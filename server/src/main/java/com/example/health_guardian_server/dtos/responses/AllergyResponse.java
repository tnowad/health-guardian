package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllergyResponse {
  private String id;
  private String userId;
  private String allergyName;
  private String severity;
  private String reactionDescription;
}
