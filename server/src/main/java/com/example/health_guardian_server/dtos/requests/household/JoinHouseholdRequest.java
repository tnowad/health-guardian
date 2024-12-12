package com.example.health_guardian_server.dtos.requests.household;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinHouseholdRequest {
  @NotBlank(message = "userId must not be null")
  @NotNull
  private String userId;
}
