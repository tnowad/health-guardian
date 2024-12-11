package com.example.health_guardian_server.dtos.requests.household;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateHouseholdRequest {
  @NotBlank(message = "Name is required")
  @NonNull
  private String name;

  @NonNull
  @NotBlank(message = "Avatar is required")
  private String avatar;

  @NonNull
  private String headId;
}
