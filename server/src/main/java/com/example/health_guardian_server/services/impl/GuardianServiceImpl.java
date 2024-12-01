package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.requests.UpdateGuardianRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.mappers.GuardianMapper;
import com.example.health_guardian_server.repositories.GuardianRepository;
import com.example.health_guardian_server.services.GuardianService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {
  private final GuardianRepository guardianRepository;
  private final GuardianMapper guardianMapper;

  @Override
  public Page<GuardianResponse> listGuardians(ListGuardiansRequest request) {
    var guardians = guardianRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(guardianMapper::toGuardianResponse);
    return guardians;
  }

  @Override
  public GuardianResponse createGuardian(CreateGuardianRequest request) {
    return guardianMapper.toGuardianResponse(
        guardianRepository.save(guardianMapper.toGuardian(request)));
  }

  @Override
  public GuardianResponse getGuardian(String guardianId) {
    return guardianRepository
        .findById(guardianId)
        .map(guardianMapper::toGuardianResponse)
        .orElseThrow(
            () -> new ResourceNotFoundException("Guardian not found for id: " + guardianId));
  }

  @Override
  public void deleteGuardian(String guardianId) {
    guardianRepository.deleteById(guardianId);
  }

  @Override
  public GuardianResponse updateGuardian(String guardianId, UpdateGuardianRequest request) {
    var guardian = guardianRepository
        .findById(guardianId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Guardian not found for id: " + guardianId));
    var updatedGuardian = guardianMapper.toGuardian(request);
    updatedGuardian.setId(guardian.getId());
    return guardianMapper.toGuardianResponse(guardianRepository.save(updatedGuardian));
  }
}
