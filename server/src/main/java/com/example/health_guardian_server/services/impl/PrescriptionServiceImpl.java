package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionStatus;
import com.example.health_guardian_server.repositories.PrescriptionRepository;
import com.example.health_guardian_server.services.PrescriptionService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;

  // Implement methods

  public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
    this.prescriptionRepository = prescriptionRepository;
  }

  @Override
  public List<Prescription> getAllPrescriptions() {
    return prescriptionRepository.findAll();
  }

  @Override
  public Prescription getPrescriptionById(String id) {
    return prescriptionRepository.findById(id).orElse(null);
  }

  @Override
  public Prescription createPrescription(Prescription prescription) {
    return prescriptionRepository.save(prescription);
  }

  @Override
  public Prescription updatePrescription(Prescription prescription) {
    return prescriptionRepository.save(prescription);
  }

  @Override
  public void deletePrescription(String id) {
    prescriptionRepository.deleteById(id);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientId(String patientId) {
    return prescriptionRepository.findByPatientId(patientId);
  }

  @Override
  public List<Prescription> getPrescriptionsByMedicationId(String medicationId) {
    return prescriptionRepository.findByMedicationId(medicationId);
  }

  @Override
  public List<Prescription> getPrescriptionsByStatus(String status) {
    return prescriptionRepository.findByStatus(PrescriptionStatus.valueOf(status));
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndMedicationId(
      String patientId, String medicationId) {
    return prescriptionRepository.findByPatientIdAndMedicationId(patientId, medicationId);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndEndDate(String patientId, Date endDate) {
    return prescriptionRepository.findByPatientIdAndEndDate(patientId, endDate);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status) {
    return prescriptionRepository.findByPatientIdAndStatus(
        patientId, PrescriptionStatus.valueOf(status));
  }
}
