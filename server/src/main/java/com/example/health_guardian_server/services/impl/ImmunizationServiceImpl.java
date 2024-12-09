package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateImmunizationRequest;
import com.example.health_guardian_server.dtos.requests.ListImmunizationsRequest;
import com.example.health_guardian_server.dtos.responses.ImmunizationResponse;
import com.example.health_guardian_server.mappers.ImmunizationMapper;
import com.example.health_guardian_server.repositories.ImmunizationRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.ImmunizationService;
import com.example.health_guardian_server.specifications.ImmunizationSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImmunizationServiceImpl implements ImmunizationService {
  private final ImmunizationRepository immunizationRepository;
  private final ImmunizationMapper immunizationMapper;
  private final UserRepository userRepository;

  @Override
  public Page<ImmunizationResponse> getAllImmunizations(ListImmunizationsRequest request) {
    log.debug("Fetching all immunizations with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    ImmunizationSpecification specification = new ImmunizationSpecification(request);

    var immunizations = immunizationRepository
        .findAll(specification, pageRequest)
        .map(immunizationMapper::toResponse);

    log.info("Fetched {} immunizations", immunizations.getTotalElements());
    return immunizations;
  }

  @Override
  public ImmunizationResponse getImmunizationById(String immunizationId) {
    log.debug("Fetching immunization with id: {}", immunizationId);
    return immunizationRepository
        .findById(immunizationId)
        .map(immunizationMapper::toResponse)
        .orElseThrow(
            () -> {
              log.error("Immunization not found with id: {}", immunizationId);
              return new ResourceNotFoundException("Immunization not found with id: " + immunizationId);
            }
        );
  }

  @Override
  public ImmunizationResponse createImmunization(CreateImmunizationRequest createImmunization) {
    log.debug("Creating immunization with request: {}", createImmunization);
    var immunization = immunizationMapper.toImmunization(createImmunization);
    var savedImmunization = immunizationRepository.save(immunization);
    log.info("Created immunization with id: {}", savedImmunization.getId());
    return immunizationMapper.toResponse(savedImmunization);
  }

  @Override
  public ImmunizationResponse updateImmunization(String id, CreateImmunizationRequest request) {
    log.debug("Updating immunization with id: {}", id);
    var immunization = immunizationRepository.findById(id)
        .orElseThrow(() -> {
          log.error("Immunization not found with id: {}", id);
          return new ResourceNotFoundException("Immunization not found with id: " + id);
        });

    immunization.setVaccineName(request.getVaccineName());
    immunization.setBatchNumber(request.getBatchNumber());
    immunization.setVaccinationDate(request.getVaccinationDate());
    immunization.setNotes(request.getNotes());

    var user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> {
          log.error("User not found with id: {}", request.getUserId());
          return new ResourceNotFoundException("User not found with id: " + request.getUserId());
        });
    immunization.setUser(user);


    var updatedImmunization = immunizationRepository.save(immunization);
    log.info("Updated immunization with id: {}", updatedImmunization.getId());
    return immunizationMapper.toResponse(updatedImmunization);
  }

  @Override
  public void deleteImmunization(String immunizationId) {
    log.debug("Deleting immunization with id: {}", immunizationId);
    immunizationRepository.deleteById(immunizationId);
    log.info("Deleted immunization with id: {}", immunizationId);
  }


}
