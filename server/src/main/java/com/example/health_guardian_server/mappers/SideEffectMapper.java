package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.SideEffectResponse;
import com.example.health_guardian_server.entities.SideEffect;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SideEffectMapper {

  SideEffectResponse toSideEffectResponse(SideEffect sideEffect);

  SideEffect toSideEffect(SideEffectResponse sideEffectResponse);
}
