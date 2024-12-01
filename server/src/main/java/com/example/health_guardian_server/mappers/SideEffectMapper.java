package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.UpdateSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.SideEffectResponse;
import com.example.health_guardian_server.entities.SideEffect;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SideEffectMapper {

  SideEffectResponse toSideEffectResponse(SideEffect sideEffect);

  SideEffect toSideEffect(SideEffectResponse sideEffectResponse);

  SideEffect toSideEffect(CreateSideEffectRequest request);

  SideEffect toSideEffect(UpdateSideEffectRequest request);
}
