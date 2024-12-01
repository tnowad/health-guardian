package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.UpdateSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.SideEffectResponse;
import com.example.health_guardian_server.entities.SideEffect;
import com.example.health_guardian_server.mappers.SideEffectMapper;
import com.example.health_guardian_server.repositories.SideEffectRepository;
import com.example.health_guardian_server.services.SideEffectService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SideEffectServiceImpl implements SideEffectService {

  private final SideEffectRepository sideEffectRepository;
  private final SideEffectMapper sideEffectMapper;

  @Override
  public Page<SideEffectResponse> listSideEffects(ListSideEffectRequest request) {
    return sideEffectRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(sideEffectMapper::toSideEffectResponse);
  }

  @Override
  public SideEffectResponse createSideEffect(CreateSideEffectRequest request) {
    SideEffect sideEffect = sideEffectMapper.toSideEffect(request);
    SideEffect savedSideEffect = sideEffectRepository.save(sideEffect);
    return sideEffectMapper.toSideEffectResponse(savedSideEffect);
  }

  @Override
  public SideEffectResponse getSideEffect(String id) {
    SideEffect sideEffect = sideEffectRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("SideEffect not found for id: " + id));
    return sideEffectMapper.toSideEffectResponse(sideEffect);
  }

  @Override
  public SideEffectResponse updateSideEffect(String id, UpdateSideEffectRequest request) {
    SideEffect existingSideEffect = sideEffectRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("SideEffect not found for id: " + id));

    SideEffect updatedSideEffect = sideEffectMapper.toSideEffect(request);
    updatedSideEffect.setId(existingSideEffect.getId());

    SideEffect savedUpdatedSideEffect = sideEffectRepository.save(updatedSideEffect);
    return sideEffectMapper.toSideEffectResponse(savedUpdatedSideEffect);
  }

  @Override
  public void deleteSideEffect(String id) {
    sideEffectRepository.deleteById(id);
  }
}
