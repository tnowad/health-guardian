package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateAllergyRequest;
import com.example.health_guardian_server.dtos.requests.ListAllergiesRequest;
import com.example.health_guardian_server.dtos.responses.AllergyResponse;
import com.example.health_guardian_server.mappers.AllergyMapper;
import com.example.health_guardian_server.repositories.AllergyRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.AllergyService;
import com.example.health_guardian_server.specifications.AllergySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllergyServiceImpl implements AllergyService {
  private final AllergyRepository allergyRepository;
  private final AllergyMapper allergyMapper;
  private final UserRepository userRepository;

  @Override
  public Page<AllergyResponse> getAllAllergies(ListAllergiesRequest request) {
    log.debug("Fetching all allergies with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    AllergySpecification specification = new AllergySpecification(request);

    var allergies =
        allergyRepository.findAll(specification, pageRequest).map(allergyMapper::toResponse);

    log.info("Fetched {} allergies", allergies.getTotalElements());
    return allergies;
  }

  @Override
  public AllergyResponse getAllergyById(String allergyId) {
    log.debug("Fetching allergy with id: {}", allergyId);
    return allergyRepository
        .findById(allergyId)
        .map(allergyMapper::toResponse)
        .orElseThrow(
            () -> {
              log.error("Allergy not found with id: {}", allergyId);
              return new ResourceNotFoundException("Allergy not found with id: " + allergyId);
            });
  }

  @Override
  public AllergyResponse createAllergy(CreateAllergyRequest createAllergy) {
    log.debug("Creating allergy with request: {}", createAllergy);
    var allergy = allergyMapper.toAllergy(createAllergy);
    var savedAllergy = allergyRepository.save(allergy);
    log.info("Created allergy with id: {}", savedAllergy.getId());
    return allergyMapper.toResponse(savedAllergy);
  }

  @Override
  public AllergyResponse updateAllergy(String allergyId, CreateAllergyRequest request) {
    log.debug("Updating allergy with id: {}", allergyId);
    var allergy =
        allergyRepository
            .findById(allergyId)
            .orElseThrow(
                () -> {
                  log.error("Allergy not found with id: {}", allergyId);
                  return new ResourceNotFoundException("Allergy not found with id: " + allergyId);
                });

    allergy.setAllergyName(request.getAllergyName());
    allergy.setReactionDescription(request.getReactionDescription());
    allergy.setSeverity(request.getSeverity());
    var user =
        userRepository
            .findById(request.getUserId())
            .orElseThrow(
                () -> {
                  log.error("User not found with id: {}", request.getUserId());
                  return new ResourceNotFoundException(
                      "User not found with id: " + request.getUserId());
                });
    allergy.setUser(user);
    // allergy.setUser(user);
    var updatedAllergy = allergyRepository.save(allergy);
    log.info("Updated allergy with id: {}", allergyId);
    return allergyMapper.toResponse(updatedAllergy);
  }

  @Override
  public void deleteAllergy(String allergyId) {
    log.debug("Deleting allergy with id: {}", allergyId);
    allergyRepository.deleteById(allergyId);
    log.info("Deleted allergy with id: {}", allergyId);
  }
}
