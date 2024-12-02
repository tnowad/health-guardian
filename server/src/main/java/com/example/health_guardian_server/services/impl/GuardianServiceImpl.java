package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.entities.Guardian;
import com.example.health_guardian_server.mappers.GuardianMapper;
import com.example.health_guardian_server.repositories.GuardianRepository;
import com.example.health_guardian_server.services.GuardianService;
import com.example.health_guardian_server.specifications.GuardianSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

  GuardianRepository guardianRepository;
  GuardianMapper guardianMapper;

  @Override
  public Page<GuardianResponse> getAllGuardians(ListGuardiansRequest request) {
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    GuardianSpecification specification = new GuardianSpecification(request);

    var guardians =
        guardianRepository
            .findAll(specification, pageRequest)
            .map(guardianMapper::toGuardianResponse);

    return guardians;
  }

  @Override
  public GuardianResponse getGuardianById(String id) {
    return guardianRepository
        .findById(id)
        .map(guardianMapper::toGuardianResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + id));
  }

  @Override
  public GuardianResponse createGuardian(GuardianResponse guardianResponse) {
    Guardian createdGuardian = guardianRepository.save(guardianMapper.toGuardian(guardianResponse));
    return guardianMapper.toGuardianResponse(createdGuardian);
  }

  @Override
  public GuardianResponse updateGuardian(String id, GuardianResponse guardianResponse) {
    Guardian existingGuardian =
        guardianRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + id));

    existingGuardian.setEmail(guardianResponse.getEmail());
    existingGuardian.setName(guardianResponse.getName());
    existingGuardian.setPhone(guardianResponse.getPhone());
    existingGuardian.setRelationshipToPatient(guardianResponse.getRelationshipToPatient());

    Guardian updatedGuardian = guardianRepository.save(existingGuardian);

    return guardianMapper.toGuardianResponse(updatedGuardian);
  }

  @Override
  public void deleteGuardian(String id) {
    guardianRepository.deleteById(id);
  }
}
