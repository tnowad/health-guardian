package com.example.health_guardian_server.dtos.responses.household;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdResponse {
  private String id;
  private String headId;
}
