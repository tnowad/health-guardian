package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateConsentFormRequest;
import com.example.health_guardian_server.dtos.requests.ListConsentFormsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateConsentFormRequest;
import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import com.example.health_guardian_server.entities.ConsentForm;
import com.example.health_guardian_server.entities.ConsentStatus;
import com.example.health_guardian_server.mappers.ConsentFormMapper;
import com.example.health_guardian_server.repositories.ConsentFormRepository;
import com.example.health_guardian_server.repositories.PatientRepository;
import com.example.health_guardian_server.services.ConsentFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsentFormServiceImpl implements ConsentFormService {
  private final ConsentFormRepository consentFormRepository;
  private final ConsentFormMapper consentFormMapper;
  private final PatientRepository patientRepository;

  @Override
  public Page<ConsentFormResponse> listConsentForms(ListConsentFormsRequest request) {
    log.debug("Fetching consent forms with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    return consentFormRepository.findAll(pageRequest).map(consentFormMapper::toConsentFormResponse);

  }

  @Override
  public ConsentFormResponse createConsentForm(CreateConsentFormRequest request) {
    log.debug("Creating consent form with request: {}", request);
    ConsentForm consentForm = consentFormMapper.toConsentForm(request);
    consentForm.setPatient(patientRepository.getReferenceById(request.getPatientId()));
    return consentFormMapper.toConsentFormResponse(consentFormRepository.save(consentForm));
  }

  @Override
  public ConsentFormResponse getConsentForm(String consentFormId) {

    log.debug("Fetching consent form with id: {}", consentFormId);
    return consentFormRepository
      .findById(consentFormId)
      .map(consentFormMapper::toConsentFormResponse)
      .orElseThrow(() -> new RuntimeException("Consent form not found"));
  }

  @Override
  public void deleteConsentForm(String consentFormId) {
    log.debug("Deleting consent form with id: {}", consentFormId);
    consentFormRepository.deleteById(consentFormId);
  }

  @Override
  public ConsentFormResponse updateConsentForm(
    String consentFormId, CreateConsentFormRequest request) {
    log.debug("Updating consent form with id: {} and request: {}", consentFormId, request);
    ConsentForm consentForm = consentFormRepository
      .findById(consentFormId)
      .orElseThrow(() -> new RuntimeException("Consent form not found"));
    consentForm.setFormName(request.getFormName());
    try{
      consentForm.setConsentDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getConsentDate()));
    } catch (Exception e) {
      log.error("Error parsing date: {}", request.getConsentDate());
    }

    consentForm.setStatus(ConsentStatus.valueOf(request.getStatus()));
    return consentFormMapper.toConsentFormResponse(consentFormRepository.save(consentForm));

  }
}
