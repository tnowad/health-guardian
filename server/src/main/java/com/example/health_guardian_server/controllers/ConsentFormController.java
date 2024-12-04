package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateConsentFormRequest;
import com.example.health_guardian_server.dtos.requests.ListConsentFormsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateConsentFormRequest;
import com.example.health_guardian_server.dtos.responses.ConsentFormResponse;
import com.example.health_guardian_server.services.ConsentFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consent-forms")
@RequiredArgsConstructor
public class ConsentFormController {

  private final ConsentFormService consentFormService;

  @GetMapping
  public ResponseEntity<Page<ConsentFormResponse>> listConsentForms(
      @ModelAttribute ListConsentFormsRequest request) {
    Page<ConsentFormResponse> response = consentFormService.listConsentForms(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ConsentFormResponse> createConsentForm(
      @RequestBody CreateConsentFormRequest request) {
    ConsentFormResponse response = consentFormService.createConsentForm(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{consentFormId}")
  public ResponseEntity<ConsentFormResponse> getConsentForm(@PathVariable String consentFormId) {
    ConsentFormResponse response = consentFormService.getConsentForm(consentFormId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{consentFormId}")
  public ResponseEntity<ConsentFormResponse> updateConsentForm(
      @PathVariable String consentFormId, @RequestBody CreateConsentFormRequest request) {
    ConsentFormResponse response = consentFormService.updateConsentForm(consentFormId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{consentFormId}")
  public ResponseEntity<Void> deleteConsentForm(@PathVariable String consentFormId) {
    consentFormService.deleteConsentForm(consentFormId);
    return ResponseEntity.noContent().build();
  }
}
