package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreatePrescriptionRequest;
import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.entities.PrescriptionStatus;
import com.example.health_guardian_server.repositories.MedicationRepository;
import com.example.health_guardian_server.repositories.PatientRepository;
import com.example.health_guardian_server.repositories.PrescriptionRepository;
import com.example.health_guardian_server.repositories.UserMedicalStaffRepository;
import com.example.health_guardian_server.services.PrescriptionService;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;
  private final PatientRepository patientRepository;

  private final MedicationRepository medicationRepository;

  private final UserMedicalStaffRepository userMedicalStaffRepository;

  private final UserRepository userRepository;
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
  public Prescription createPrescription(CreatePrescriptionRequest request) {
    log.debug("Creating prescription for patient with id: {}", request.getPatientId());
    Optional<Patient> patient = patientRepository.findById(request.getPatientId());
    Optional<Medication> medication = medicationRepository.findById(request.getMedicationId());
    Optional<UserMedicalStaff> userMedicalStaff = userMedicalStaffRepository.findById(request.getPrescribedBy());
    Optional<User> prescribedBy = userMedicalStaff.map(UserMedicalStaff::getUser);

    if (patient.isPresent() && medication.isPresent() && userMedicalStaff.isPresent() && prescribedBy.isPresent()) {
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
      Prescription savedPrescription = prescriptionRepository.save(prescription);
      log.info("Created prescription with id: {}", savedPrescription.getId());
      return savedPrescription;
    } else {
      log.warn("Failed to create prescription: required entities not found");
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
