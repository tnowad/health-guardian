package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.repositories.PrescriptionRepository;
import com.example.health_guardian_server.services.PrescriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public List<Prescription> getPrescriptionsByDoctorId(String doctorId) {
    return prescriptionRepository.findByDoctorId(doctorId);
  }

  @Override
  public List<Prescription> getPrescriptionsByMedicationId(String medicationId) {
    return prescriptionRepository.findByMedicationId(medicationId);
  }

  @Override
  public List<Prescription> getPrescriptionsByStatus(String status) {
    return prescriptionRepository.findByStatus(status);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndDoctorId(String patientId, String doctorId) {
    return prescriptionRepository.findByPatientIdAndDoctorId(patientId, doctorId);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndMedicationId(String patientId, String medicationId) {
    return prescriptionRepository.findByPatientIdAndMedicationId(patientId, medicationId);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndDate(String patientId, String date) {
    return prescriptionRepository.findByPatientIdAndDate(patientId, date);
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status) {
    return prescriptionRepository.findByPatientIdAndStatus(patientId, status);
  }


}
