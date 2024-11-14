package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.repositories.PatientRepository;
import com.example.health_guardian_server.services.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
  PatientRepository patientRepository;

  @Override
  public Patient createPatient(String id, String firstName, String lastName) {
    Patient patient = Patient.builder().id(id).firstName(firstName).lastName(lastName).build();
  }
}
