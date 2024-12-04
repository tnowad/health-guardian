package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListAggregatedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.AggregatedSideEffectResponse;
import com.example.health_guardian_server.mappers.AggregatedSideEffectMapper;
import com.example.health_guardian_server.repositories.AggregatedSideEffectRepository;
import com.example.health_guardian_server.services.AggregatedSideEffectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j  // Enables logging for this class
public class AggregatedSideEffectServiceImpl implements AggregatedSideEffectService {

  private final AggregatedSideEffectRepository aggregatedSideEffectRepository;
  private final AggregatedSideEffectMapper aggregatedSideEffectMapper;

  @Override
  public Page<AggregatedSideEffectResponse> listAggregatedSideEffects(
    ListAggregatedSideEffectsRequest request) {
    log.debug("Listing aggregated side effects with request: {}", request);

    var aggregatedSideEffects = aggregatedSideEffectRepository
      .findAll(request.toSpecification(), request.toPageable())
      .map(aggregatedSideEffectMapper::toAggregatedSideEffectResponse);

    log.info("Fetched {} aggregated side effects", aggregatedSideEffects.getTotalElements());

    return aggregatedSideEffects;
  }

  @Override
  public AggregatedSideEffectResponse createAggregatedSideEffect(
    CreateAggregatedSideEffectRequest request) {
    log.debug("Creating aggregated side effect with request: {}", request);

    var aggregatedSideEffect = aggregatedSideEffectMapper.toAggregatedSideEffect(request);
    var savedAggregatedSideEffect = aggregatedSideEffectRepository.save(aggregatedSideEffect);

    log.info("Created aggregated side effect with id: {}", savedAggregatedSideEffect.getId());

    return aggregatedSideEffectMapper.toAggregatedSideEffectResponse(savedAggregatedSideEffect);
  }

  @Override
  public AggregatedSideEffectResponse getAggregatedSideEffect(String aggregatedSideEffectId) {
    log.debug("Fetching aggregated side effect with id: {}", aggregatedSideEffectId);

    return aggregatedSideEffectRepository
      .findById(aggregatedSideEffectId)
      .map(aggregatedSideEffectMapper::toAggregatedSideEffectResponse)
      .orElseThrow(
        () -> {
          log.warn("AggregatedSideEffect not found for id: {}", aggregatedSideEffectId);
          return new ResourceNotFoundException(
            "AggregatedSideEffect not found for id: " + aggregatedSideEffectId);
        });
  }

  @Override
  public void deleteAggregatedSideEffect(String aggregatedSideEffectId) {
    log.debug("Deleting aggregated side effect with id: {}", aggregatedSideEffectId);

    try {
      aggregatedSideEffectRepository.deleteById(aggregatedSideEffectId);
      log.info("Successfully deleted aggregated side effect with id: {}", aggregatedSideEffectId);
    } catch (Exception e) {
      log.error("Error occurred while deleting aggregated side effect with id: {}", aggregatedSideEffectId, e);
    }
  }

  @Override
  public AggregatedSideEffectResponse updateAggregatedSideEffect(
    String aggregatedSideEffectId, CreateAggregatedSideEffectRequest request) {
    log.debug("Updating aggregated side effect with id: {} using request: {}", aggregatedSideEffectId, request);

    var aggregatedSideEffect = aggregatedSideEffectRepository
      .findById(aggregatedSideEffectId)
      .orElseThrow(
        () -> {
          log.warn("AggregatedSideEffect not found for id: {}", aggregatedSideEffectId);
          return new ResourceNotFoundException(
            "AggregatedSideEffect not found for id: " + aggregatedSideEffectId);
        });

    var updatedAggregatedSideEffect = aggregatedSideEffectMapper.toAggregatedSideEffect(request);
    updatedAggregatedSideEffect.setId(aggregatedSideEffect.getId());

    var savedUpdatedAggregatedSideEffect = aggregatedSideEffectRepository.save(updatedAggregatedSideEffect);
    log.info("Successfully updated aggregated side effect with id: {}", savedUpdatedAggregatedSideEffect.getId());

    return aggregatedSideEffectMapper.toAggregatedSideEffectResponse(savedUpdatedAggregatedSideEffect);
  }
}
