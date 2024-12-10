package com.example.health_guardian_server.dtos.responses.household;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdMemberResponse {
  private String id;
  private String householdId;
  private String userId;
  private String relationshipToPatient;
}
