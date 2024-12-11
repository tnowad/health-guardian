package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdResponse;
import com.example.health_guardian_server.services.HouseholdService;
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
@RequestMapping("/households")
@RequiredArgsConstructor
public class HouseholdController {

  private final HouseholdService householdService;

  @GetMapping
  public ResponseEntity<Page<HouseholdResponse>> listHouseholds(
      @ModelAttribute ListHouseholdsRequest request) {
    Page<HouseholdResponse> response = householdService.listHouseholds(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<HouseholdResponse> createHousehold(
      @RequestBody CreateHouseholdRequest request) {
    HouseholdResponse response = householdService.createHousehold(request);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{householdId}")
  public ResponseEntity<HouseholdResponse> updateHousehold(
      @PathVariable String householdId, @RequestBody CreateHouseholdRequest request) {
    HouseholdResponse response = householdService.updateHousehold(householdId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{householdId}")
  public ResponseEntity<Void> deleteHousehold(@PathVariable String householdId) {
    householdService.deleteHousehold(householdId);
    return ResponseEntity.noContent().build();
  }
}
