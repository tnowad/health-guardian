package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateMedicationRequest;
import com.example.health_guardian_server.dtos.requests.UpdateMedicationRequest;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.entities.Medication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

  MedicationResponse toMedicationResponse(Medication medication);

  Medication toMedication(MedicationResponse medicationResponse);

  Medication toMedication(CreateMedicationRequest request);

  Medication toMedication(UpdateMedicationRequest request);

  static SimpleResponse toMedicationSimpleResponse(Medication medication) {
    return SimpleResponse.builder()
      .id(medication.getId())
      .message("MedicationId: " + medication.getId())
      .build();
  }
}
