package com.example.health_guardian_server.dtos.requests.household;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateHouseholdMemberRequest {
  @NonNull
  @NotBlank(message = "Household id is required")
  private String householdId;

  @NotBlank(message = "Email is required")
  @NonNull
  private String email;
}
