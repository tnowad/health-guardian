package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Medication;

import java.util.List;

public interface MedicationService {
  // Define methods

  List<Medication> getAllMedications();

  Medication getMedicationById(String id);

  Medication createMedication(Medication medication);

  Medication updateMedication(Medication medication);

  void deleteMedication(String id);

  List<Medication> getMedicationsByName(String name);
  
}
