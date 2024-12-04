package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreatePrescriptionRequest;
import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.PrescriptionService;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;

  private final PatientRepository patientRepository;

  private final MedicationRepository medicationRepository;

  private final UserMedicalStaffRepository userMedicalStaffRepository;

  private final UserRepository userRepository;

  @Override
  public List<Prescription> getAllPrescriptions() {
    return prescriptionRepository.findAll();
  }

  @Override
  public Prescription getPrescriptionById(String id) {
    return prescriptionRepository.findById(id).orElse(null);
  }

  @Override
  public Prescription createPrescription(CreatePrescriptionRequest request) {
    Optional<Patient> patient = patientRepository.findById(request.getPatientId());
    System.out.println(patient.get().getId());
    Optional<Medication> medication = medicationRepository.findById(request.getMedicationId());
    Optional<UserMedicalStaff> userMedicalStaff = userMedicalStaffRepository.findById(request.getPrescribedBy());
    Optional<User> prescribedBy = userRepository.findById(userMedicalStaff.get().getUser().getId());
    if (patient.isPresent() && medication.isPresent() && userMedicalStaff.isPresent()) {
      Prescription prescription = Prescription.builder()
        .patient(patient.get())
        .medication(medication.get())
        .prescribedBy(prescribedBy.get())
        .dosage(request.getDosage())
        .frequency(request.getFrequency())
        .startDate(request.getStartDate())
        .endDate(request.getEndDate())
        .status(request.getStatus())
        .build();
      return prescriptionRepository.save(prescription);
    }
    else {
      return null;
    }
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
