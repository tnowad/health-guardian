package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicationRequest {

  @NotBlank(message = "name of medication must not be blank")
  @NotNull(message = "name of medication must not be null")
  private String name;

  private String activeIngredient;

  private String dosageForm;

  private String standardDosage;

  private String manufacturer;
}
