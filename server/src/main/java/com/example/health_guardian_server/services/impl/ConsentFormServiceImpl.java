package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateConsentFormRequest;
import com.example.health_guardian_server.dtos.requests.ListConsentFormsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateConsentFormRequest;
import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import com.example.health_guardian_server.services.ConsentFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsentFormServiceImpl implements ConsentFormService {

  @Override
  public Page<ConsentFormResponse> listConsentForms(ListConsentFormsRequest request) {
    log.debug("Listing consent forms with request: {}", request);
    throw new UnsupportedOperationException("Unimplemented method 'listConsentForms'");
  }

  @Override
  public ConsentFormResponse createConsentForm(CreateConsentFormRequest request) {
    log.debug("Creating consent form with request: {}", request);
    throw new UnsupportedOperationException("Unimplemented method 'createConsentForm'");
  }

  @Override
  public ConsentFormResponse getConsentForm(String consentFormId) {
    log.debug("Fetching consent form with id: {}", consentFormId);
    throw new UnsupportedOperationException("Unimplemented method 'getConsentForm'");
  }

  @Override
  public void deleteConsentForm(String consentFormId) {
    log.debug("Deleting consent form with id: {}", consentFormId);
    throw new UnsupportedOperationException("Unimplemented method 'deleteConsentForm'");
  }

  @Override
  public ConsentFormResponse updateConsentForm(
    String consentFormId, UpdateConsentFormRequest request) {
    log.debug("Updating consent form with id: {} and request: {}", consentFormId, request);
    throw new UnsupportedOperationException("Unimplemented method 'updateConsentForm'");
  }
}
