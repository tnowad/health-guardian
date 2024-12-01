package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListAggregatedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.AggregatedSideEffectResponse;
import org.springframework.data.domain.Page;

public interface AggregatedSideEffectService {

  Page<AggregatedSideEffectResponse> listAggregatedSideEffects(
      ListAggregatedSideEffectsRequest request);

  AggregatedSideEffectResponse createAggregatedSideEffect(
      CreateAggregatedSideEffectRequest request);

  AggregatedSideEffectResponse getAggregatedSideEffect(String aggregatedSideEffectId);

  void deleteAggregatedSideEffect(String aggregatedSideEffectId);

  AggregatedSideEffectResponse updateAggregatedSideEffect(
      String aggregatedSideEffectId, UpdateAggregatedSideEffectRequest request);
}
