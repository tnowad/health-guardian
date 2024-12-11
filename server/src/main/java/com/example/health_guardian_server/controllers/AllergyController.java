package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.allergy.CreateAllergyRequest;
import com.example.health_guardian_server.dtos.requests.allergy.ListAllergiesRequest;
import com.example.health_guardian_server.dtos.responses.allergy.AllergyResponse;
import com.example.health_guardian_server.services.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/allergies")
public class AllergyController {
  private final AllergyService allergyService;

  @GetMapping
  public ResponseEntity<Page<AllergyResponse>> listAllergies(@ModelAttribute ListAllergiesRequest request) {
    Page<AllergyResponse> allergies = allergyService.getAllAllergies(request);
    return ResponseEntity.ok(allergies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AllergyResponse> getAllergy(String id) {
    AllergyResponse allergy = allergyService.getAllergyById(id);
    return ResponseEntity.ok(allergy);
  }

  @PostMapping
  public ResponseEntity<AllergyResponse> createAllergy(@RequestBody CreateAllergyRequest request) {
    AllergyResponse allergy = allergyService.createAllergy(request);
    return ResponseEntity.ok(allergy);
  }

  @PutMapping
  public ResponseEntity<AllergyResponse> updateAllergy(@PathVariable String id, @RequestBody CreateAllergyRequest request) {
    AllergyResponse allergy = allergyService.updateAllergy(id, request);
    return ResponseEntity.ok(allergy);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAllergy(@PathVariable String id) {
    allergyService.deleteAllergy(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<AllergyResponse>> listAllergiesByUserId(String userId, ListAllergiesRequest request) {
    Page<AllergyResponse> allergies = allergyService.getAllAllergiesByUserId(userId, request);
    return ResponseEntity.ok(allergies);
  }
}
