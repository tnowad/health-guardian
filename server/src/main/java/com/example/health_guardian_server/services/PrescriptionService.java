package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Prescription;

import java.util.List;

public interface PrescriptionService {
  // Define methods
  List<Prescription> getAllPrescriptions();

  Prescription getPrescriptionById(String id);

  Prescription createPrescription(Prescription prescription);

  Prescription updatePrescription(Prescription prescription);

  void deletePrescription(String id);

  List<Prescription> getPrescriptionsByPatientId(String patientId);

  List<Prescription> getPrescriptionsByDoctorId(String doctorId);

  List<Prescription> getPrescriptionsByMedicationId(String medicationId);

  List<Prescription> getPrescriptionsByStatus(String status);

  List<Prescription> getPrescriptionsByPatientIdAndDoctorId(String patientId, String doctorId);

  List<Prescription> getPrescriptionsByPatientIdAndMedicationId(String patientId, String medicationId);

  List<Prescription> getPrescriptionsByPatientIdAndDate(String patientId, String date);

  List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status);


}
