package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationResponse {

  private String id;
  private String name;
  private String activeIngredient;
  private String dosageForm;
  private String standardDosage;
  private String manufacturer;
}
