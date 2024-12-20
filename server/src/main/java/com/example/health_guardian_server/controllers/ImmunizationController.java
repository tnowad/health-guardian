package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.immunization.CreateImmunizationRequest;
import com.example.health_guardian_server.dtos.requests.immunization.ListImmunizationsRequest;
import com.example.health_guardian_server.dtos.responses.immunization.ImmunizationResponse;
import com.example.health_guardian_server.services.ImmunizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/immunizations")
@RequiredArgsConstructor
public class ImmunizationController {
  private final ImmunizationService immunizationService;

  @GetMapping
  public ResponseEntity<Page<ImmunizationResponse>> listImmunizations(@ModelAttribute ListImmunizationsRequest request) {
    Page<ImmunizationResponse> immunizations = immunizationService.getAllImmunizations(request);
    return ResponseEntity.ok(immunizations);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ImmunizationResponse> getImmunization(@PathVariable String id) {
    ImmunizationResponse immunization = immunizationService.getImmunizationById(id);
    return ResponseEntity.ok(immunization);
  }

  @PostMapping
  public ResponseEntity<ImmunizationResponse> createImmunization(@RequestBody CreateImmunizationRequest request) {
    ImmunizationResponse immunization = immunizationService.createImmunization(request);
    return ResponseEntity.ok(immunization);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ImmunizationResponse> updateImmunization(@PathVariable String id,@RequestBody CreateImmunizationRequest request) {
    ImmunizationResponse immunization = immunizationService.updateImmunization(id, request);
    return ResponseEntity.ok(immunization);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteImmunization(@PathVariable String id) {
    immunizationService.deleteImmunization(id);
    return ResponseEntity.noContent().build();
  }
}
