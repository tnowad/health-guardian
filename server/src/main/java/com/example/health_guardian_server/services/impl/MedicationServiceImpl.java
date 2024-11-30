package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.repositories.MedicationRepository;
import com.example.health_guardian_server.services.MedicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
  private final MedicationRepository medicationRepository;

  public MedicationServiceImpl(MedicationRepository medicationRepository) {
    this.medicationRepository = medicationRepository;
  }
  // Implement methods

  @Override
  public List<Medication> getAllMedications() {
    return medicationRepository.findAll();
  }

  @Override
  public Medication getMedicationById(String id) {
    return medicationRepository.findById(id).orElse(null);
  }

  @Override
  public Medication createMedication(Medication medication) {
    return medicationRepository.save(medication);
  }

  @Override
  public Medication updateMedication(Medication medication) {
    return medicationRepository.save(medication);
  }

  @Override
  public void deleteMedication(String id) {
    medicationRepository.deleteById(id);
  }

  @Override
  public List<Medication> getMedicationsByName(String name) {
    return medicationRepository.findByName(name);
  }

}
