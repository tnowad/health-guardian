package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.UpdateSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.SideEffectResponse;
import org.springframework.data.domain.Page;

public interface SideEffectService {

  Page<SideEffectResponse> listSideEffects(ListSideEffectRequest request);

  SideEffectResponse getSideEffect(String sideEffectId);

  SideEffectResponse createSideEffect(CreateSideEffectRequest request);

  void deleteSideEffect(String sideEffectId);

  SideEffectResponse updateSideEffect(String sideEffectId, UpdateSideEffectRequest request);
}
