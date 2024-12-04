package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

@Data
public class CreateMedicationRequest {
  private String name;

  private String activeIngredient;

  private String dosageForm;

  private String standardDosage;

  private String manufacturer;
}
