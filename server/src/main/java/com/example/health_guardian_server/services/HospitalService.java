package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateHospitalRequest;
import com.example.health_guardian_server.dtos.requests.ListHospitalRequest;
import com.example.health_guardian_server.dtos.responses.HospitalResponse;
import com.example.health_guardian_server.entities.Hospital;
import org.springframework.data.domain.Page;

public interface HospitalService {
  // Define methods

  Page<HospitalResponse> getAllHospitals(ListHospitalRequest request);

  HospitalResponse getHospitalById(String id);

  HospitalResponse createHospital(CreateHospitalRequest hospital);

  HospitalResponse updateHospital(String id, HospitalResponse hospital);

  void deleteHospital(String id);

}
