package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.immunization.CreateImmunizationRequest;
import com.example.health_guardian_server.dtos.requests.immunization.ListImmunizationsRequest;
import com.example.health_guardian_server.dtos.responses.immunization.ImmunizationResponse;
import org.springframework.data.domain.Page;

public interface ImmunizationService {

  Page<ImmunizationResponse> getAllImmunizations(ListImmunizationsRequest request);

  ImmunizationResponse getImmunizationById(String id);

  ImmunizationResponse createImmunization(CreateImmunizationRequest createImmunization);

  ImmunizationResponse updateImmunization(String id, CreateImmunizationRequest request);

  void deleteImmunization(String id);
}
