package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateHospitalRequest;
import com.example.health_guardian_server.dtos.requests.ListHospitalRequest;
import com.example.health_guardian_server.dtos.responses.HospitalResponse;
import com.example.health_guardian_server.entities.Hospital;
import com.example.health_guardian_server.repositories.HospitalRepository;
import com.example.health_guardian_server.services.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {
  private final HospitalRepository hospitalRepository;

  public HospitalServiceImpl(HospitalRepository hospitalRepository) {
    this.hospitalRepository = hospitalRepository;
  }

  @Override
  public Page<HospitalResponse> getAllHospitals(ListHospitalRequest request) {
    log.debug("Fetching all hospitals with page: {}, size: {}", request.getPage(), request.getSize());
    Page<Hospital> hospitals =
      hospitalRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
    log.info("Found {} hospitals", hospitals.getTotalElements());
    return hospitals.map(
      hospital ->
        new HospitalResponse(
          hospital.getId(),
          hospital.getName(),
          hospital.getLocation(),
          hospital.getPhone(),
          hospital.getEmail()));
  }

  @Override
  public HospitalResponse getHospitalById(String id) {
    log.debug("Fetching hospital with ID: {}", id);
    return hospitalRepository
      .findById(id)
      .map(
        hospital -> {
          log.info("Hospital found with ID: {}", id);
          return new HospitalResponse(
            hospital.getId(),
            hospital.getName(),
            hospital.getLocation(),
            hospital.getPhone(),
            hospital.getEmail());
        })
      .orElseGet(() -> {
        log.warn("No hospital found with ID: {}", id);
        return null;
      });
  }

  @Override
  public HospitalResponse createHospital(CreateHospitalRequest hospital) {
    log.debug("Creating new hospital: {}", hospital);
    Hospital newHospital =
      Hospital.builder()
        .name(hospital.getName())
        .location(hospital.getLocation())
        .phone(hospital.getPhone())
        .email(hospital.getEmail())
        .build();
    Hospital savedHospital = hospitalRepository.save(newHospital);
    log.info("Hospital created with ID: {}", savedHospital.getId());
    return new HospitalResponse(
      savedHospital.getId(),
      savedHospital.getName(),
      savedHospital.getLocation(),
      savedHospital.getPhone(),
      savedHospital.getEmail());
  }

  @Override
  public HospitalResponse updateHospital(String id, HospitalResponse hospital) {
    log.debug("Updating hospital with ID: {}", id);
    Hospital existingHospital = hospitalRepository.findById(id).orElse(null);
    if (existingHospital == null) {
      log.warn("No hospital found with ID: {}", id);
      return null;
    }
    log.info("Hospital found with ID: {}, updating details", id);
    existingHospital.setName(hospital.getName());
    existingHospital.setLocation(hospital.getLocation());
    existingHospital.setPhone(hospital.getPhone());
    existingHospital.setEmail(hospital.getEmail());
    Hospital updatedHospital = hospitalRepository.save(existingHospital);
    log.info("Hospital updated with ID: {}", updatedHospital.getId());
    return new HospitalResponse(
      updatedHospital.getId(),
      updatedHospital.getName(),
      updatedHospital.getLocation(),
      updatedHospital.getPhone(),
      updatedHospital.getEmail());
  }

  @Override
  public void deleteHospital(String id) {
    log.debug("Deleting hospital with ID: {}", id);
    if (hospitalRepository.existsById(id)) {
      hospitalRepository.deleteById(id);
      log.info("Hospital deleted with ID: {}", id);
    } else {
      log.warn("No hospital found with ID: {}. Delete operation aborted.", id);
    }
  }
}
