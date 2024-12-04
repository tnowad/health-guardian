package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreatePatientRequest;
import com.example.health_guardian_server.dtos.requests.ListPatientRequest;
import com.example.health_guardian_server.dtos.responses.PatientResponse;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.mappers.PatientMapper;
import com.example.health_guardian_server.repositories.PatientRepository;
import com.example.health_guardian_server.services.PatientService;
import com.example.health_guardian_server.specifications.PatientSpecification;
import java.time.LocalDate;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // Add the Slf4j annotation to enable logging
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;

  @Override
  public Patient createPatient(String id, String firstName, String lastName) {
    log.info("Creating patient with id: {}, first name: {}, last name: {}", id, firstName, lastName);
    Patient patient = Patient.builder().id(id).firstName(firstName).lastName(lastName).build();
    Patient savedPatient = patientRepository.save(patient);
    log.info("Patient created successfully with id: {}", savedPatient.getId());
    return savedPatient;
  }

  @Override
  public PatientResponse createPatient(CreatePatientRequest patientRequest) {
    log.debug("Creating patient from request: {}", patientRequest);
    Patient newPatient = patientMapper.toPatient(patientRequest);
    newPatient.setCreatedAt(
      Date.from(LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
    newPatient.setUpdatedAt(
      Date.from(LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
    Patient createdPatient = patientRepository.save(newPatient);
    PatientResponse patientResponse = patientMapper.toPatientResponse(createdPatient);
    log.info("Patient created successfully with id: {}", createdPatient.getId());
    return patientResponse;
  }

  @Override
  public Page<PatientResponse> getAllPatients(ListPatientRequest request) {
    log.debug("Fetching patients with pagination: page = {}, size = {}", request.getPage(), request.getSize());
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    PatientSpecification specification = new PatientSpecification(request);
    Page<PatientResponse> patients = patientRepository.findAll(specification, pageRequest)
      .map(patientMapper::toPatientResponse);
    log.info("Fetched {} patients", patients.getTotalElements());
    return patients;
  }

  @Override
  public PatientResponse getPatientById(String id) {
    log.debug("Fetching patient with id: {}", id);
    return patientRepository
      .findById(id)
      .map(patientMapper::toPatientResponse)
      .orElseThrow(() -> {
        log.error("Patient not found with id: {}", id);
        return new ResourceNotFoundException("Patient not found with id " + id);
      });
  }

  @Override
  public PatientResponse updatePatient(String id, Patient patient) {
    log.debug("Updating patient with id: {}", id);
    Patient existingPatient =
      patientRepository
        .findById(id)
        .orElseThrow(() -> {
          log.error("Patient not found with id: {}", id);
          return new ResourceNotFoundException("Patient not found with id " + id);
        });

    existingPatient.setFirstName(patient.getFirstName());
    existingPatient.setLastName(patient.getLastName());
    existingPatient.setGender(patient.getGender());
    existingPatient.setDob(patient.getDob());
    existingPatient.setGuardian(patient.getGuardian());
    existingPatient.setUpdatedAt(
      Date.from(LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
    Patient updatedPatient = patientRepository.save(existingPatient);
    PatientResponse patientResponse = patientMapper.toPatientResponse(updatedPatient);
    log.info("Patient updated successfully with id: {}", updatedPatient.getId());
    return patientResponse;
  }

  @Override
  public void deletePatient(String id) {
    log.info("Deleting patient with id: {}", id);
    if (!patientRepository.existsById(id)) {
      log.error("Attempted to delete non-existing patient with id: {}", id);
      throw new ResourceNotFoundException("Patient not found with id " + id);
    }
    patientRepository.deleteById(id);
    log.info("Patient with id: {} deleted successfully", id);
  }
}
