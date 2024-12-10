package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.surgery.CreateSurgeryRequest;
import com.example.health_guardian_server.dtos.requests.surgery.ListSurgeriesRequest;
import com.example.health_guardian_server.dtos.responses.surgery.SurgeryResponse;
import org.springframework.data.domain.Page;

public interface SurgeryService {

  Page<SurgeryResponse> getAllSurgeries(ListSurgeriesRequest request);

  SurgeryResponse getSurgeryById(String id);

  SurgeryResponse createSurgery(CreateSurgeryRequest createSurgery);

  SurgeryResponse updateSurgery(String id, CreateSurgeryRequest request);

  void deleteSurgery(String id);
}
