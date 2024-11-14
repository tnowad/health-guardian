package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Patient;

public interface PatientService {

  Patient createPatient(String userId, String firstName, String lastName);
}
