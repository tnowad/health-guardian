package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.MedicationRequest;
import com.example.health_guardian_server.dtos.responses.GetListCommonResponse;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.repositories.MedicationRepository;
import com.example.health_guardian_server.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
  private final MedicationRepository medicationRepository;

  @Override
  public GetListCommonResponse<MedicationResponse> getListMedication(int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);

    var medications = medicationRepository.findAll(pageable).getContent().stream()
        .map(
            medication -> MedicationResponse.builder()
                .id(medication.getId())
                .name(medication.getName())
                .activeIngredient(medication.getActiveIngredient())
                .dosageForm(medication.getDosageForm())
                .standardDosage(medication.getStandardDosage())
                .manufacturer(medication.getManufacturer())
                .build())
        .toList();

    return GetListCommonResponse.<MedicationResponse>builder()
        .items(medications)
        .message("Success")
        .build();
  }

  @Override
  public MedicationResponse createMedication(MedicationRequest medicationRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createMedication'");
  }

  @Override
  public MedicationResponse updateMedication(MedicationRequest medicationRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateMedication'");
  }

  @Override
  public void deleteMedication(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteMedication'");
  }

  @Override
  public Medication getMedicationById(String id) {
    return medicationRepository.findById(id).orElseThrow();
  }
}
