package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.MedicationRequest;
import com.example.health_guardian_server.dtos.responses.GetListCommonResponse;
import com.example.health_guardian_server.dtos.responses.MedicationResponse;
import com.example.health_guardian_server.services.MedicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MedicationController {

  MedicationService medicationService;

  @GetMapping
  public ResponseEntity<GetListCommonResponse<MedicationResponse>> getMedications(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
    return ResponseEntity.ok().body(medicationService.getListMedication(page, pageSize));
  }

  @PostMapping("/create")
  public ResponseEntity<MedicationResponse> createMedication(
      @RequestBody MedicationRequest medicationResquest) {
    return ResponseEntity.ok().body(medicationService.createMedication(medicationResquest));
  }

  @PostMapping("/update")
  public ResponseEntity<MedicationResponse> updateMedication(
      @RequestBody MedicationRequest medicationResquest) {
    return ResponseEntity.ok().body(medicationService.updateMedication(medicationResquest));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteMedication(@PathVariable Long id) {
    medicationService.deleteMedication(id);
    return ResponseEntity.ok().body("Success");
  }
}
