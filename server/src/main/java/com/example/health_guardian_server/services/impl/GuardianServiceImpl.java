package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.entities.Guardian;
import com.example.health_guardian_server.mappers.GuardianMapper;
import com.example.health_guardian_server.repositories.GuardianRepository;
import com.example.health_guardian_server.services.GuardianService;
import com.example.health_guardian_server.specifications.GuardianSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuardianServiceImpl implements GuardianService {

  private final GuardianRepository guardianRepository;
  private final GuardianMapper guardianMapper;

  @Override
  public Page<GuardianResponse> getAllGuardians(ListGuardiansRequest request) {
    log.debug("Fetching all guardians with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    GuardianSpecification specification = new GuardianSpecification(request);

    var guardians =
      guardianRepository
        .findAll(specification, pageRequest)
        .map(guardianMapper::toGuardianResponse);

    log.info("Retrieved {} guardians", guardians.getTotalElements());
    return guardians;
  }

  @Override
  public GuardianResponse getGuardianById(String id) {
    log.debug("Fetching guardian with id: {}", id);
    return guardianRepository
      .findById(id)
      .map(guardianMapper::toGuardianResponse)
      .orElseThrow(() -> {
        log.error("Guardian not found with id: {}", id);
        return new ResourceNotFoundException("Guardian not found with id " + id);
      });
  }

  @Override
  public GuardianResponse createGuardian(CreateGuardianRequest createGuardian) {
    log.debug("Creating guardian with details: {}", createGuardian);
    Guardian guardian = guardianMapper.toGuardian(createGuardian);
    Guardian createdGuardian = guardianRepository.save(guardian);
    log.info("Guardian created with id: {}", createdGuardian.getId());
    return guardianMapper.toGuardianResponse(createdGuardian);
  }

  @Override
  public GuardianResponse updateGuardian(String id, CreateGuardianRequest createGuardianRequest) {
    log.debug("Updating guardian with id: {}", id);
    Guardian existingGuardian =
      guardianRepository
        .findById(id)
        .orElseThrow(() -> {
          log.error("Guardian not found with id: {}", id);
          return new ResourceNotFoundException("Guardian not found with id " + id);
        });

    log.debug("Updating details for guardian with id: {}", id);
    existingGuardian.setEmail(createGuardianRequest.getEmail());
    existingGuardian.setName(createGuardianRequest.getName());
    existingGuardian.setPhone(createGuardianRequest.getPhone());
    existingGuardian.setRelationshipToPatient(createGuardianRequest.getRelationshipToPatient());

    Guardian updatedGuardian = guardianRepository.save(existingGuardian);
    log.info("Guardian updated with id: {}", updatedGuardian.getId());
    return guardianMapper.toGuardianResponse(updatedGuardian);
  }

  @Override
  public void deleteGuardian(String id) {
    log.debug("Deleting guardian with id: {}", id);
    guardianRepository.deleteById(id);
    log.info("Guardian deleted with id: {}", id);
  }
}
