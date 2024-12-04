package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateMedicationRequest;
import com.example.health_guardian_server.dtos.requests.ListMedicationRequest;
import com.example.health_guardian_server.dtos.requests.UpdateMedicationRequest;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.mappers.MedicationMapper;
import com.example.health_guardian_server.repositories.MedicationRepository;
import com.example.health_guardian_server.services.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicationServiceImpl implements MedicationService {
  private final MedicationRepository medicationRepository;
  private final MedicationMapper medicationMapper;

  @Override
  public Page<MedicationResponse> listMedications(ListMedicationRequest request) {
    log.info("Listing medications with request parameters: {}", request);

    Page<MedicationResponse> medications = medicationRepository
      .findAll(request.toSpecification(), request.toPageable())
      .map(medicationMapper::toMedicationResponse);

    log.debug("Found {} medications", medications.getTotalElements());
    return medications;
  }

  @Override
  public MedicationResponse createMedication(CreateMedicationRequest request) {
    log.info("Creating new medication with details: {}", request);

    MedicationResponse response = medicationMapper.toMedicationResponse(
      medicationRepository.save(medicationMapper.toMedication(request)));

    log.debug("Medication created successfully with id: {}", response.getId());
    return response;
  }

  @Override
  public MedicationResponse getMedication(String medicationId) {
    log.info("Fetching medication with id: {}", medicationId);

    MedicationResponse response = medicationRepository
      .findById(medicationId)
      .map(medicationMapper::toMedicationResponse)
      .orElseThrow(
        () -> {
          log.error("Medication not found for id: {}", medicationId);
          return new ResourceNotFoundException("Medication not found for id: " + medicationId);
        });

    log.debug("Medication fetched successfully: {}", response);
    return response;
  }

  @Override
  public MedicationResponse updateMedication(String medicationId, UpdateMedicationRequest request) {
    log.info("Updating medication with id: {} and request details: {}", medicationId, request);

    var medication = medicationRepository
      .findById(medicationId)
      .orElseThrow(
        () -> {
          log.error("Medication not found for id: {}", medicationId);
          return new ResourceNotFoundException("Medication not found for id: " + medicationId);
        });

    var updatedMedication = medicationMapper.toMedication(request);
    updatedMedication.setId(medication.getId());

    MedicationResponse response = medicationMapper.toMedicationResponse(medicationRepository.save(updatedMedication));

    log.debug("Medication updated successfully with id: {}", response.getId());
    return response;
  }

  @Override
  public Medication deleteMedication(String medicationId) {
    log.info("Deleting medication with id: {}", medicationId);
    Optional<Medication> medication = medicationRepository.findById(medicationId);
    if (!medication.isPresent()) {
      log.error("Medication not found for id: {}", medicationId);
      throw new ResourceNotFoundException("Medication not found for id: " + medicationId);
    }

    medicationRepository.deleteById(medicationId);
    log.debug("Medication with id: {} deleted successfully", medicationId);

    return medication.get();
  }
}
