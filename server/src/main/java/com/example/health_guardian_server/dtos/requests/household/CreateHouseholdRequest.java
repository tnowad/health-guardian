package com.example.health_guardian_server.dtos.requests.household;

import lombok.Data;

@Data
public class CreateHouseholdRequest {
  private String name;
  private String headId;
}
