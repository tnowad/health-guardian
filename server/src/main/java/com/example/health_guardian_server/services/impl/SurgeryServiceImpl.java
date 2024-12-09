package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateSurgeryRequest;
import com.example.health_guardian_server.dtos.requests.ListSurgeriesRequest;
import com.example.health_guardian_server.dtos.responses.SurgeryResponse;
import com.example.health_guardian_server.mappers.SurgeryMapper;
import com.example.health_guardian_server.repositories.SurgeryRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.SurgeryService;
import com.example.health_guardian_server.specifications.SurgerySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurgeryServiceImpl implements SurgeryService {
  private final SurgeryRepository surgeryRepository;
  private final SurgeryMapper surgeryMapper;
  private final UserRepository userRepository;

  @Override
  public Page<SurgeryResponse> getAllSurgeries(ListSurgeriesRequest request) {
    log.debug("Fetching all surgeries with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    SurgerySpecification specification = new SurgerySpecification(request);

    var surgeries = surgeryRepository
        .findAll(specification, pageRequest)
        .map(surgeryMapper::toResponse);

    log.info("Fetched {} surgeries", surgeries.getTotalElements());
    return surgeries;
  }

  @Override
  public SurgeryResponse getSurgeryById(String surgeryId) {
    log.debug("Fetching surgery with id: {}", surgeryId);
    return surgeryRepository
        .findById(surgeryId)
        .map(surgeryMapper::toResponse)
        .orElseThrow(
            () -> {
              log.error("Surgery not found with id: {}", surgeryId);
              return new ResourceNotFoundException("Surgery not found with id: " + surgeryId);
            }
        );
  }

  @Override
  public SurgeryResponse createSurgery(CreateSurgeryRequest createSurgery) {
    log.debug("Creating surgery with request: {}", createSurgery);
    var surgery = surgeryMapper.toSurgery(createSurgery);
    var savedSurgery = surgeryRepository.save(surgery);
    log.info("Created surgery with id: {}", savedSurgery.getId());
    return surgeryMapper.toResponse(savedSurgery);
  }

  @Override
  public SurgeryResponse updateSurgery(String surgeryId, CreateSurgeryRequest request) {
    log.debug("Updating surgery with id: {} and request: {}", surgeryId, request);
    var surgery = surgeryRepository
        .findById(surgeryId)
        .orElseThrow(
            () -> {
              log.error("Surgery not found with id: {}", surgeryId);
              return new ResourceNotFoundException("Surgery not found with id: " + surgeryId);
            }
        );
    surgery.setDate(request.getDate());
    surgery.setNotes(request.getNotes());
    surgery.setDescription(request.getDescription());

    var user = userRepository.findById(request.getUserId())
        .orElseThrow(
            () -> {
              log.error("User not found with id: {}", request.getUserId());
              return new ResourceNotFoundException("User not found with id: " + request.getUserId());
            }
        );
    surgery.setUser(user);
    var updatedSurgery = surgeryRepository.save(surgery);
    log.info("Updated surgery with id: {}", updatedSurgery.getId());
    return surgeryMapper.toResponse(updatedSurgery);
  }

  @Override
  public void deleteSurgery(String surgeryId) {
    log.debug("Deleting surgery with id: {}", surgeryId);
    surgeryRepository.deleteById(surgeryId);
    log.info("Deleted surgery with id: {}", surgeryId);
  }
}
