package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.pass_condition.CreatePastConditionRequest;
import com.example.health_guardian_server.dtos.requests.pass_condition.ListPastConditionsRequest;
import com.example.health_guardian_server.dtos.responses.pass_condition.PastConditionResponse;
import com.example.health_guardian_server.mappers.PastConditionMapper;
import com.example.health_guardian_server.repositories.PastConditionRepository;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.services.PastConditionService;
import com.example.health_guardian_server.specifications.PastConditionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassConditionServiceImpl implements PastConditionService {
  private final PastConditionRepository pastConditionRepository;
  private final PastConditionMapper pastConditionMapper;
  private final UserRepository userRepository;

  @Override
  public Page<PastConditionResponse> getAllPastConditions(ListPastConditionsRequest request) {
    log.debug("Fetching all past conditions with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    PastConditionSpecification specification = new PastConditionSpecification(request);

    var pastConditions = pastConditionRepository
        .findAll(specification, pageRequest)
        .map(pastConditionMapper::toResponse);

    log.info("Fetched {} past conditions", pastConditions.getTotalElements());
    return pastConditions;
  }

  @Override
  public PastConditionResponse getPastConditionById(String pastConditionId) {
    log.debug("Fetching past condition with id: {}", pastConditionId);
    return pastConditionRepository
        .findById(pastConditionId)
        .map(pastConditionMapper::toResponse)
        .orElseThrow(
            () -> {
              log.error("Past condition not found with id: {}", pastConditionId);
              return new ResourceNotFoundException("Past condition not found with id: " + pastConditionId);
            }
        );
  }

  @Override
  public PastConditionResponse createPastCondition(CreatePastConditionRequest createPastCondition) {
    log.debug("Creating past condition with request: {}", createPastCondition);
    var pastCondition = pastConditionMapper.toPastCondition(createPastCondition);
    var savedPastCondition = pastConditionRepository.save(pastCondition);
    log.info("Created past condition with id: {}", savedPastCondition.getId());
    return pastConditionMapper.toResponse(savedPastCondition);
  }

  @Override
  public PastConditionResponse updatePastCondition(String pastConditionId, CreatePastConditionRequest createPastCondition) {
    log.debug("Updating past condition with id: {}", pastConditionId);
    var pastCondition = pastConditionRepository.findById(pastConditionId)
        .orElseThrow(() -> {
          log.error("Past condition not found with id: {}", pastConditionId);
          return new ResourceNotFoundException("Past condition not found with id: " + pastConditionId);
        });
    pastCondition.setCondition(createPastCondition.getCondition());
    pastCondition.setDescription(createPastCondition.getDescription());
    pastCondition.setDateDiagnosed(createPastCondition.getDateDiagnosed());
    var user = userRepository.findById(createPastCondition.getUserId())
        .orElseThrow(() -> {
          log.error("User not found with id: {}", createPastCondition.getUserId());
          return new ResourceNotFoundException("User not found with id: " + createPastCondition.getUserId());
        });
    pastCondition.setUser(user);
    var updatedPastCondition = pastConditionRepository.save(pastCondition);
    log.info("Updated past condition with id: {}", updatedPastCondition.getId());
    return pastConditionMapper.toResponse(updatedPastCondition);
  }

  @Override
  public void deletePastCondition(String pastConditionId) {
    log.debug("Deleting past condition with id: {}", pastConditionId);
    pastConditionRepository.deleteById(pastConditionId);
    log.info("Deleted past condition with id: {}", pastConditionId);
  }
}
