package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateConsentFormRequest;
import com.example.health_guardian_server.dtos.requests.ListConsentFormsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateConsentFormRequest;
import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import com.example.health_guardian_server.services.ConsentFormService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ConsentFormServiceImpl implements ConsentFormService {

  @Override
  public Page<ConsentFormResponse> listConsentForms(ListConsentFormsRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listConsentForms'");
  }

  @Override
  public ConsentFormResponse createConsentForm(CreateConsentFormRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createConsentForm'");
  }

  @Override
  public ConsentFormResponse getConsentForm(String consentFormId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getConsentForm'");
  }

  @Override
  public void deleteConsentForm(String consentFormId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteConsentForm'");
  }

  @Override
  public ConsentFormResponse updateConsentForm(
      String consentFormId, UpdateConsentFormRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateConsentForm'");
  }
}
