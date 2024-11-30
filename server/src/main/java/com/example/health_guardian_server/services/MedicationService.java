package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.MedicationRequest;
import com.example.health_guardian_server.dtos.responses.GetListCommonResponse;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.entities.Medication;

public interface MedicationService {

  GetListCommonResponse<MedicationResponse> getListMedication(int page, int pageSize);

  MedicationResponse createMedication(MedicationRequest medicationRequest);

  MedicationResponse updateMedication(MedicationRequest medicationRequest);

  void deleteMedication(String id);

  Medication getMedicationById(String id);
}
