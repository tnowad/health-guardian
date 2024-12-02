package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import com.example.health_guardian_server.entities.ConsentForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsentFormMapper {

  ConsentFormResponse toConsentFormResponse(ConsentForm consentForm);

  ConsentForm toConsentForm(ConsentFormResponse consentFormResponse);
}
