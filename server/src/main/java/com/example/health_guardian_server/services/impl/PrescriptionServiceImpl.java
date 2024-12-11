package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.User;

import com.example.health_guardian_server.mappers.PrescriptionMapper;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.PrescriptionService;
import com.example.health_guardian_server.specifications.PrescriptionSpecification;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;

  private final UserRepository userRepository;

  private final PrescriptionMapper prescriptionMapper;

  @Override
  public Page<PrescriptionResponse> getAllPrescriptions(ListPrescriptionRequest request) {
    log.debug("Fetching all visit summaries with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    PrescriptionSpecification specification = new PrescriptionSpecification((request));

    var prescriptions = prescriptionRepository
        .findAll(specification, pageRequest)
        .map(prescriptionMapper::toPrescriptionResponse);

    log.info("Fetched {} visit summaries", prescriptions.getTotalElements());
    return prescriptions;
  }

  @Override
  public Prescription getPrescriptionById(String id) {
    var prescription = prescriptionRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("Prescription not found with id: {}", id);
              return new ResourceNotFoundException("Prescription not found with id " + id);
            });
    return prescription;
  }

  @Override
  public Prescription createPrescription(CreatePrescriptionRequest request) {
    log.debug("Creating visit summary: {}", request);
    Optional<User> user = userRepository.findById(request.getUserId());
    if (user.get() == null) {
      log.error("User not found with id: {}", request.getUserId());
      throw new RuntimeException("User not found with id: " + request.getUserId());
    }
    var prescription = prescriptionRepository.save(
        Prescription.builder()
            .user(user.get())
            .issuedBy(request.getIssuedBy())
            .issuedDate(request.getIssuedDate())
            .validUntil(request.getValidUntil())
            .status(request.getStatus())
            .build());
    prescriptionRepository.save(prescription);
    log.info("Visit summary created with id: {}", prescription.getId());
    return prescription;
  }

  @Override
  public void deletePrescription(String id) {
    log.debug("Deleting household with id: {}", id);
    Prescription prescription = prescriptionRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("Prescription not found with id: {}", id);
              return new ResourceNotFoundException("Prescription not found with id " + id);
            });

    prescriptionRepository.delete(prescription);
    log.info("Prescription deleted with id: {}", id);
  }

  @Override
  public Prescription updatePrescription(String prescriptionId, CreatePrescriptionRequest request) {
    log.debug("Updating visit summary with id: {}", prescriptionId);
    Prescription prescription = prescriptionRepository
        .findById(prescriptionId)
        .orElseThrow(
            () -> {
              log.error("Prescription not found with id: {}", prescriptionId);
              return new RuntimeException("Prescription not found with id: " + prescriptionId);
            });
    prescriptionMapper.toPrescription(request);
    return prescriptionRepository.save(prescription);
  }
}
