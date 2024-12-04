package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.PrescriptionResponse;
import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.mappers.PrescriptionMapper;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.PrescriptionService;
import com.example.health_guardian_server.specifications.PrescriptionSpecification;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // Add the Slf4j annotation to enable logging
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;
  private final PatientRepository patientRepository;
  private final MedicationRepository medicationRepository;

  private final UserMedicalStaffRepository userMedicalStaffRepository;

  private final UserRepository userRepository;
  private final PrescriptionMapper prescriptionMapper;

  @Override
  public Page<PrescriptionResponse> getAllPrescriptions(ListPrescriptionRequest request) {
    log.debug("Fetching all prescriptions");
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    PrescriptionSpecification specification = new PrescriptionSpecification(request);
    var prescriptions =
        prescriptionRepository
            .findAll(specification, pageRequest)
            .map(prescriptionMapper::toPrescriptionResponse);

    log.info("Fetched {} prescriptions", prescriptions);
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
  public Prescription createPrescription(CreatePrescriptionRequest request) {
    log.debug("Creating prescription for patient with id: {}", request.getPatientId());
    Optional<Patient> patient = patientRepository.findById(request.getPatientId());
    Optional<Medication> medication = medicationRepository.findById(request.getMedicationId());
    Optional<UserMedicalStaff> userMedicalStaff =
        userMedicalStaffRepository.findById(request.getPrescribedBy());
    Optional<User> prescribedBy = userMedicalStaff.map(UserMedicalStaff::getUser);
    if (patient.isPresent()
        && medication.isPresent()
        && userMedicalStaff.isPresent()
        && prescribedBy.isPresent()) {
      Prescription prescription =
          Prescription.builder()
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
    log.debug(
        "Fetching prescriptions for patient id: {} and medication id: {}", patientId, medicationId);
    List<Prescription> prescriptions =
        prescriptionRepository.findByPatientIdAndMedicationId(patientId, medicationId);
    log.info(
        "Fetched {} prescriptions for patient id: {} and medication id: {}",
        prescriptions.size(),
        patientId,
        medicationId);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndEndDate(String patientId, Date endDate) {
    log.debug("Fetching prescriptions for patient id: {} and end date: {}", patientId, endDate);
    List<Prescription> prescriptions =
        prescriptionRepository.findByPatientIdAndEndDate(patientId, endDate);
    log.info(
        "Fetched {} prescriptions for patient id: {} and end date: {}",
        prescriptions.size(),
        patientId,
        endDate);
    return prescriptions;
  }

  @Override
  public List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status) {
    log.debug("Fetching prescriptions for patient id: {} and status: {}", patientId, status);
    List<Prescription> prescriptions =
        prescriptionRepository.findByPatientIdAndStatus(
            patientId, PrescriptionStatus.valueOf(status));
    log.info(
        "Fetched {} prescriptions for patient id: {} and status: {}",
        prescriptions.size(),
        patientId,
        status);
    return prescriptions;
  }
}
