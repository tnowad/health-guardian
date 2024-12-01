package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.ListPatientRequest;
import com.example.health_guardian_server.dtos.responses.PatientResponse;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.mappers.PatientMapper;
import com.example.health_guardian_server.repositories.PatientRepository;
import com.example.health_guardian_server.services.PatientService;
import com.example.health_guardian_server.specifications.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
  PatientRepository patientRepository;
  PatientMapper patientMapper;

  @Override
  public Patient createPatient(String id, String firstName, String lastName) {
    Patient patient = Patient.builder().id(id).firstName(firstName).lastName(lastName).build();
    return patientRepository.save(patient);
  }

  @Override
  public Page<PatientResponse> getAllPatients(ListPatientRequest request) {
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    PatientSpecification specification = new PatientSpecification(request);

    var patients = patientRepository.findAll(specification, pageRequest).map(patientMapper::toPatientResponse);
    return patients;
  }
  @Override
  public PatientResponse getPatientById(String id) {
    return patientRepository.findById(id)
      .map(patientMapper::toPatientResponse)
      .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
  }

  @Override
  public PatientResponse updatePatient(String id, Patient patient) {
    Patient existingPatient = patientRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));

    existingPatient.setFirstName(patient.getFirstName());
    existingPatient.setLastName(patient.getLastName());
    existingPatient.setGender(patient.getGender());
    existingPatient.setDob(patient.getDob());
    existingPatient.setGuardian(patient.getGuardian());
    existingPatient.setUpdatedAt(Date.from(LocalDate.now().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
    Patient updatedPatient = patientRepository.save(existingPatient);
    return patientMapper.toPatientResponse(updatedPatient);
  }

  @Override
  public void deletePatient(String id) {
    patientRepository.deleteById(id);
  }



}
