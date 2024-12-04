package com.example.health_guardian_server.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMedicationRequest {
  private String id;

  private String name;

  private String activeIngredient;

  private String dosageForm;

  private String standardDosage;

  private String manufacturer;
}
