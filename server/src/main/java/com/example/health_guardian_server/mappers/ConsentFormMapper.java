package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateConsentFormRequest;
import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import com.example.health_guardian_server.entities.ConsentForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsentFormMapper {

  @Mapping(source = "patient.id", target = "patientId")

  ConsentFormResponse toConsentFormResponse(ConsentForm consentForm);

  ConsentForm toConsentForm(CreateConsentFormRequest consentFormResponse);
}
