package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.responses.GetListMedicationResponse;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.repositories.MedicationRepository;
import com.example.health_guardian_server.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
  private final MedicationRepository medicationRepository;

  @Override
  public GetListMedicationResponse getMedications() {

    var medications =
        medicationRepository.findAll().stream()
            .map(
                medication ->
                    MedicationResponse.builder()
                        .id(medication.getId())
                        .name(medication.getName())
                        .activeIngredient(medication.getActiveIngredient())
                        .dosageForm(medication.getDosageForm())
                        .standardDosage(medication.getStandardDosage())
                        .manufacturer(medication.getManufacturer())
                        .build())
            .toList();

    return GetListMedicationResponse.builder().items(medications).message("Success").build();
  }
}
