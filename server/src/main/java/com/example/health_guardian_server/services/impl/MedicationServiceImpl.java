package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateMedicationRequest;
import com.example.health_guardian_server.dtos.requests.ListMedicationRequest;
import com.example.health_guardian_server.dtos.requests.UpdateMedicationRequest;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.mappers.MedicationMapper;
import com.example.health_guardian_server.repositories.MedicationRepository;
import com.example.health_guardian_server.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
  private final MedicationRepository medicationRepository;
  private final MedicationMapper medicationMapper;

  @Override
  public Page<MedicationResponse> listMedications(ListMedicationRequest request) {
    Page<MedicationResponse> medications = medicationRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(medicationMapper::toMedicationResponse);
    return medications;
  }

  @Override
  public MedicationResponse createMedication(CreateMedicationRequest request) {
    return medicationMapper.toMedicationResponse(
        medicationRepository.save(medicationMapper.toMedication(request)));
  }

  @Override
  public MedicationResponse getMedication(String medicationId) {
    return medicationRepository
        .findById(medicationId)
        .map(medicationMapper::toMedicationResponse)
        .orElseThrow(
            () -> new ResourceNotFoundException("Medication not found for id: " + medicationId));
  }

  @Override
  public MedicationResponse updateMedication(String medicationId, UpdateMedicationRequest request) {
    return medicationMapper.toMedicationResponse(
        medicationRepository.save(medicationMapper.toMedication(request)));
  }

  @Override
  public void deleteMedication(String medicationId) {
    medicationRepository.deleteById(medicationId);
  }
}
