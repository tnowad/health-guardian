package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateHospitalRequest;
import com.example.health_guardian_server.dtos.requests.ListHospitalRequest;
import com.example.health_guardian_server.dtos.responses.HospitalResponse;
import com.example.health_guardian_server.entities.Hospital;
import com.example.health_guardian_server.repositories.HospitalRepository;
import com.example.health_guardian_server.services.HospitalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {
  // Implement methods
  private final HospitalRepository hospitalRepository;

  public HospitalServiceImpl(HospitalRepository hospitalRepository) {
    this.hospitalRepository = hospitalRepository;
  }

  @Override
  public Page<HospitalResponse> getAllHospitals(ListHospitalRequest request) {
    Page<Hospital> hospitals = hospitalRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
    return hospitals.map(hospital -> new HospitalResponse(hospital.getId(),hospital.getName(),hospital.getLocation(),hospital.getPhone(),hospital.getEmail()));
  }

  @Override
  public HospitalResponse getHospitalById(String id) {
    return hospitalRepository.findById(id)
      .map(hospital -> new HospitalResponse(hospital.getId(),hospital.getName(),hospital.getLocation(),hospital.getPhone(),hospital.getEmail()))
      .orElse(null);
  }

  @Override
  public HospitalResponse createHospital(CreateHospitalRequest hospital) {
    Hospital newHospital = Hospital.builder()
      .name(hospital.getName())
      .location(hospital.getLocation())
      .phone(hospital.getPhone())
      .email(hospital.getEmail())
      .build();
    return new HospitalResponse(hospitalRepository.save(newHospital).getId(),newHospital.getName(),newHospital.getLocation(),newHospital.getPhone(),newHospital.getEmail());
  }

  @Override
  public HospitalResponse updateHospital(String id, HospitalResponse hospital) {
    Hospital existingHospital = hospitalRepository.findById(id).orElse(null);
    if (existingHospital == null) {
      return null;
    }
    existingHospital.setName(hospital.getName());
    existingHospital.setLocation(hospital.getLocation());
    existingHospital.setPhone(hospital.getPhone());
    existingHospital.setEmail(hospital.getEmail());
    return new HospitalResponse(hospitalRepository.save(existingHospital).getId(),existingHospital.getName(),existingHospital.getLocation(),existingHospital.getPhone(),existingHospital.getEmail());
  }

  @Override
  public void deleteHospital(String id) {
    hospitalRepository.deleteById(id);
  }
}
