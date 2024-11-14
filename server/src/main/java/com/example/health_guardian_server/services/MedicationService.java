package com.example.health_guardian_server.services;


import com.example.health_guardian_server.dtos.responses.GetListMedicationResponse;

public interface MedicationService {

  GetListMedicationResponse getMedications();
}
