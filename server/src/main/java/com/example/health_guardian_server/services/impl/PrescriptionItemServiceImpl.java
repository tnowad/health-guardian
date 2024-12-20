package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionItemRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionItemRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionItemResponse;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionItem;
import com.example.health_guardian_server.mappers.PrescriptionItemMapper;
import com.example.health_guardian_server.repositories.PrescriptionItemRepository;
import com.example.health_guardian_server.services.PrescriptionItemService;
import com.example.health_guardian_server.services.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionItemServiceImpl implements PrescriptionItemService {
  private final PrescriptionItemRepository prescriptionItemRepository;

  private final PrescriptionService prescriptionService;

  private final PrescriptionItemMapper prescriptionItemMapper;

  @Override
  public Page<PrescriptionItemResponse> getAllPrescriptionItems(
      ListPrescriptionItemRequest request) {
    log.debug("Fetching all visit summaries with request: {}", request);
    return prescriptionItemRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(prescriptionItemMapper::toPrescriptionItemResponse);
  }

  @Override
  public PrescriptionItem getPrescriptionItemById(String id) {
    var prescriptionItem = prescriptionItemRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("Prescription item not found with id: {}", id);
              return new ResourceNotFoundException("Prescription not found with id " + id);
            });
    return prescriptionItem;
  }

  @Override
  public PrescriptionItem createPrescriptionItem(CreatePrescriptionItemRequest request) {
    log.debug("Creating visit summary: {}", request);
    Prescription prescription = prescriptionService.getPrescriptionById(request.getPrescriptionId());

    var prescriptionItem = prescriptionItemRepository.save(
        PrescriptionItem.builder()
            .prescription(prescription)
            .medicationName(request.getMedicationName())
            .dosage(request.getDosage())
            .frequency(request.getFrequency())
            .status(request.getStatus())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .build());
    prescriptionItemRepository.save(prescriptionItem);
    log.info("Visit summary created with id: {}", prescription.getId());
    return prescriptionItem;
  }

  @Override
  public void deletePrescriptionItem(String id) {
    log.debug("Deleting household with id: {}", id);
    PrescriptionItem prescription = prescriptionItemRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("PrescriptionItem not found with id: {}", id);
              return new ResourceNotFoundException("PrescriptionItem not found with id " + id);
            });

    prescriptionItemRepository.delete(prescription);
    log.info("PrescriptionItem deleted with id: {}", id);
  }

  @Override
  public PrescriptionItem updatePrescriptionItem(
      String prescriptionItemId, CreatePrescriptionItemRequest request) {
    log.debug("Updating visit summary with id: {}", prescriptionItemId);
    PrescriptionItem prescriptionItem = prescriptionItemRepository
        .findById(prescriptionItemId)
        .orElseThrow(
            () -> {
              log.error("PrescriptionItem not found with id: {}", prescriptionItemId);
              return new RuntimeException(
                  "PrescriptionItem not found with id: " + prescriptionItemId);
            });
    prescriptionItemMapper.toPrescriptionItem(request);
    return prescriptionItemRepository.save(prescriptionItem);
  }
}
