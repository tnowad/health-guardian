package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PrescriptionService {
  // Define methods
  Page<PrescriptionResponse> getAllPrescriptions(ListPrescriptionRequest request);

  Prescription getPrescriptionById(String id);

  Prescription createPrescription(CreatePrescriptionRequest request);

  Prescription updatePrescription(Prescription prescription);

  void deletePrescription(String id);

  List<Prescription> getPrescriptionsByPatientId(String patientId);

  List<Prescription> getPrescriptionsByMedicationId(String medicationId);

  List<Prescription> getPrescriptionsByStatus(String status);

  List<Prescription> getPrescriptionsByPatientIdAndMedicationId(
      String patientId, String medicationId);

  List<Prescription> getPrescriptionsByPatientIdAndEndDate(String patientId, Date endDate);

  List<Prescription> getPrescriptionsByPatientIdAndStatus(String patientId, String status);
}
