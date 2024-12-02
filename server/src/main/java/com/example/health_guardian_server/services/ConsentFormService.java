package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateConsentFormRequest;
import com.example.health_guardian_server.dtos.requests.ListConsentFormsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateConsentFormRequest;
import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import org.springframework.data.domain.Page;

public interface ConsentFormService {

  Page<ConsentFormResponse> listConsentForms(ListConsentFormsRequest request);

  ConsentFormResponse createConsentForm(CreateConsentFormRequest request);

  ConsentFormResponse getConsentForm(String consentFormId);

  void deleteConsentForm(String consentFormId);

  ConsentFormResponse updateConsentForm(String consentFormId, UpdateConsentFormRequest request);
}
