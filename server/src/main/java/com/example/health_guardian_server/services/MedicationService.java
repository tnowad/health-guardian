package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateMedicationRequest;
import com.example.health_guardian_server.dtos.requests.ListMedicationRequest;
import com.example.health_guardian_server.dtos.requests.UpdateMedicationRequest;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.entities.Medication;
import org.springframework.data.domain.Page;

public interface MedicationService {
  Page<MedicationResponse> listMedications(ListMedicationRequest request);

  MedicationResponse createMedication(CreateMedicationRequest request);

  MedicationResponse getMedication(String medicationId);

  MedicationResponse updateMedication(String medicationId, UpdateMedicationRequest request);

  Medication deleteMedication(String medicationId);
}
