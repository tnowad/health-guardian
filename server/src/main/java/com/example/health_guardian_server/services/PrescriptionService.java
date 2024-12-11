package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import org.springframework.data.domain.Page;

public interface PrescriptionService {

  Page<PrescriptionResponse> getAllPrescriptions(ListPrescriptionRequest request);

  Prescription getPrescriptionById(String id);

  Prescription createPrescription(CreatePrescriptionRequest request);

  void deletePrescription(String id);

  Prescription updatePrescription(String prescriptionId, CreatePrescriptionRequest request);
}
