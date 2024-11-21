package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Prescription;

import java.util.Date;
import java.util.List;

public interface PrescriptionService {
  // Define methods
  List<Prescription> getAllPrescriptions();

  Prescription getPrescriptionById(String id);

  Prescription createPrescription(Prescription prescription);

  Prescription updatePrescription(Prescription prescription);

  void deletePrescription(String id);

  List<Prescription> getPrescriptionsByPatientId(String patientId);

  List<Prescription> getPrescriptionsByMedicationId(String medicationId);

  List<Prescription> getPrescriptionsByStatus(String status);

  List<Prescription> getPrescriptionsByPatientIdAndMedicationId(String patientId, String medicationId);

  List<Prescription> getPrescriptionsByPatientIdAndEndDate(String patientId, Date endDate);

  List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status);


}
