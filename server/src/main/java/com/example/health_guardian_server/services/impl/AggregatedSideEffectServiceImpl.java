package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListAggregatedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.AggregatedSideEffectResponse;
import com.example.health_guardian_server.mappers.AggregatedSideEffectMapper;
import com.example.health_guardian_server.repositories.AggregatedSideEffectRepository;
import com.example.health_guardian_server.services.AggregatedSideEffectService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AggregatedSideEffectServiceImpl implements AggregatedSideEffectService {

  private final AggregatedSideEffectRepository aggregatedSideEffectRepository;
  private final AggregatedSideEffectMapper aggregatedSideEffectMapper;

  @Override
  public Page<AggregatedSideEffectResponse> listAggregatedSideEffects(
      ListAggregatedSideEffectsRequest request) {
    var aggregatedSideEffects = aggregatedSideEffectRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(aggregatedSideEffectMapper::toAggregatedSideEffectResponse);
    return aggregatedSideEffects;
  }

  @Override
  public AggregatedSideEffectResponse createAggregatedSideEffect(
      CreateAggregatedSideEffectRequest request) {
    return aggregatedSideEffectMapper.toAggregatedSideEffectResponse(
        aggregatedSideEffectRepository.save(
            aggregatedSideEffectMapper.toAggregatedSideEffect(request)));
  }

  @Override
  public AggregatedSideEffectResponse getAggregatedSideEffect(String aggregatedSideEffectId) {
    return aggregatedSideEffectRepository
        .findById(aggregatedSideEffectId)
        .map(aggregatedSideEffectMapper::toAggregatedSideEffectResponse)
        .orElseThrow(
            () -> new ResourceNotFoundException(
                "AggregatedSideEffect not found for id: " + aggregatedSideEffectId));
  }

  @Override
  public void deleteAggregatedSideEffect(String aggregatedSideEffectId) {
    aggregatedSideEffectRepository.deleteById(aggregatedSideEffectId);
  }

  @Override
  public AggregatedSideEffectResponse updateAggregatedSideEffect(
      String aggregatedSideEffectId, UpdateAggregatedSideEffectRequest request) {
    var aggregatedSideEffect = aggregatedSideEffectRepository
        .findById(aggregatedSideEffectId)
        .orElseThrow(
            () -> new ResourceNotFoundException(
                "AggregatedSideEffect not found for id: " + aggregatedSideEffectId));
    var updatedAggregatedSideEffect = aggregatedSideEffectMapper.toAggregatedSideEffect(request);
    updatedAggregatedSideEffect.setId(aggregatedSideEffect.getId());
    return aggregatedSideEffectMapper.toAggregatedSideEffectResponse(
        aggregatedSideEffectRepository.save(updatedAggregatedSideEffect));
  }
}
