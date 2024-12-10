package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.allergy.CreateAllergyRequest;
import com.example.health_guardian_server.dtos.requests.allergy.ListAllergiesRequest;
import com.example.health_guardian_server.dtos.responses.allergy.AllergyResponse;
import org.springframework.data.domain.Page;

public interface AllergyService {
  Page<AllergyResponse> getAllAllergies(ListAllergiesRequest request);

  AllergyResponse getAllergyById(String id);

  AllergyResponse createAllergy(CreateAllergyRequest createAllergy);

  AllergyResponse updateAllergy(String id, CreateAllergyRequest request);

  void deleteAllergy(String id);

}
