package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionStatus;
import com.example.health_guardian_server.repositories.PrescriptionRepository;
import com.example.health_guardian_server.services.PrescriptionService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // Add the Slf4j annotation to enable logging
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;

  // Implement methods

  @Override
  public List<Prescription> getAllPrescriptions() {
    log.debug("Fetching all prescriptions");
    List<Prescription> prescriptions = prescriptionRepository.findAll();
    log.info("Fetched {} prescriptions", prescriptions.size());
    return prescriptions;
  }

  @Override
  public Prescription getPrescriptionById(String id) {
    log.debug("Fetching prescription with id: {}", id);
    Prescription prescription = prescriptionRepository.findById(id).orElse(null);
    if (prescription == null) {
      log.warn("Prescription with id {} not found", id);
    } else {
      log.info("Fetched prescription with id: {}", id);
    }
    return prescription;
  }

  @Override
  public Prescription createPrescription(Prescription prescription) {
    log.debug("Creating prescription for patient with id: {}", prescription.getPatient().getId());
    Prescription createdPrescription = prescriptionRepository.save(prescription);
    log.info("Created prescription with id: {}", createdPrescription.getId());
    return createdPrescription;
  }

  @Override
  public Prescription updatePrescription(Prescription prescription) {
    log.debug("Updating prescription with id: {}", prescription.getId());
    Prescription updatedPrescription = prescriptionRepository.save(prescription);
    log.info("Updated prescription with id: {}", updatedPrescription.getId());
    return updatedPrescription;
  }

  @Override
  public void deletePrescription(String id) {
    log.debug("Deleting prescription with id: {}", id);
    prescriptionRepository.deleteById(id);
    log.info("Deleted prescription with id: {}", id);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientId(String patientId) {
    log.debug("Fetching prescriptions for patient id: {}", patientId);
    List<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId);
    log.info("Fetched {} prescriptions for patient id: {}", prescriptions.size(), patientId);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByMedicationId(String medicationId) {
    log.debug("Fetching prescriptions for medication id: {}", medicationId);
    List<Prescription> prescriptions = prescriptionRepository.findByMedicationId(medicationId);
    log.info("Fetched {} prescriptions for medication id: {}", prescriptions.size(), medicationId);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByStatus(String status) {
    log.debug("Fetching prescriptions with status: {}", status);
    List<Prescription> prescriptions =
      prescriptionRepository.findByStatus(PrescriptionStatus.valueOf(status));
    log.info("Fetched {} prescriptions with status: {}", prescriptions.size(), status);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndMedicationId(
    String patientId, String medicationId) {
    log.debug("Fetching prescriptions for patient id: {} and medication id: {}", patientId, medicationId);
    List<Prescription> prescriptions = prescriptionRepository.findByPatientIdAndMedicationId(patientId, medicationId);
    log.info("Fetched {} prescriptions for patient id: {} and medication id: {}", prescriptions.size(), patientId, medicationId);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndEndDate(String patientId, Date endDate) {
    log.debug("Fetching prescriptions for patient id: {} and end date: {}", patientId, endDate);
    List<Prescription> prescriptions = prescriptionRepository.findByPatientIdAndEndDate(patientId, endDate);
    log.info("Fetched {} prescriptions for patient id: {} and end date: {}", prescriptions.size(), patientId, endDate);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status) {
    log.debug("Fetching prescriptions for patient id: {} and status: {}", patientId, status);
    List<Prescription> prescriptions =
      prescriptionRepository.findByPatientIdAndStatus(patientId, PrescriptionStatus.valueOf(status));
    log.info("Fetched {} prescriptions for patient id: {} and status: {}", prescriptions.size(), patientId, status);
    return prescriptions;
  }
}
