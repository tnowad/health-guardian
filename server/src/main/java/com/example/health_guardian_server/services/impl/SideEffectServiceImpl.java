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
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // Enable logging for the class
public class SideEffectServiceImpl implements SideEffectService {

  private final SideEffectRepository sideEffectRepository;
  private final SideEffectMapper sideEffectMapper;

  @Override
  public Page<SideEffectResponse> listSideEffects(ListSideEffectRequest request) {
    log.debug("Listing side effects with request: {}", request);
    Page<SideEffectResponse> sideEffectsPage = sideEffectRepository
      .findAll(request.toSpecification(), request.toPageable())
      .map(sideEffectMapper::toSideEffectResponse);
    log.debug("Found {} side effects", sideEffectsPage.getTotalElements());
    return sideEffectsPage;
  }

  @Override
  public SideEffectResponse createSideEffect(CreateSideEffectRequest request) {
    log.debug("Creating side effect with request: {}", request);
    SideEffect sideEffect = sideEffectMapper.toSideEffect(request);
    SideEffect savedSideEffect = sideEffectRepository.save(sideEffect);
    SideEffectResponse response = sideEffectMapper.toSideEffectResponse(savedSideEffect);
    log.info("Side effect created with id: {}", savedSideEffect.getId());
    return response;
  }

  @Override
  public SideEffectResponse getSideEffect(String id) {
    log.debug("Fetching side effect with id: {}", id);
    SideEffect sideEffect = sideEffectRepository
      .findById(id)
      .orElseThrow(() -> {
        log.error("SideEffect not found for id: {}", id);
        return new ResourceNotFoundException("SideEffect not found for id: " + id);
      });
    SideEffectResponse response = sideEffectMapper.toSideEffectResponse(sideEffect);
    log.info("Side effect fetched with id: {}", id);
    return response;
  }

  @Override
  public SideEffectResponse updateSideEffect(String id, UpdateSideEffectRequest request) {
    log.debug("Updating side effect with id: {} and request: {}", id, request);
    SideEffect existingSideEffect = sideEffectRepository
      .findById(id)
      .orElseThrow(() -> {
        log.error("SideEffect not found for id: {}", id);
        return new ResourceNotFoundException("SideEffect not found for id: " + id);
      });

    SideEffect updatedSideEffect = sideEffectMapper.toSideEffect(request);
    updatedSideEffect.setId(existingSideEffect.getId());

    SideEffect savedUpdatedSideEffect = sideEffectRepository.save(updatedSideEffect);
    SideEffectResponse response = sideEffectMapper.toSideEffectResponse(savedUpdatedSideEffect);
    log.info("Side effect updated with id: {}", id);
    return response;
  }

  @Override
  public void deleteSideEffect(String id) {
    log.debug("Deleting side effect with id: {}", id);
    sideEffectRepository.deleteById(id);
    log.info("Side effect with id: {} deleted", id);
  }
}
