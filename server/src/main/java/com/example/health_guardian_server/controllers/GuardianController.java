package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.requests.UpdateGuardianRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.services.GuardianService;
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
@RequestMapping("/guardians")
@RequiredArgsConstructor
public class GuardianController {

  private final GuardianService guardianService;

  // Get all guardians (list)
  @GetMapping
  public ResponseEntity<Page<GuardianResponse>> listGuardians(
      @ModelAttribute ListGuardiansRequest request) {
    Page<GuardianResponse> response = guardianService.listGuardians(request);
    return ResponseEntity.ok(response);
  }

  // Create a new guardian
  @PostMapping
  public ResponseEntity<GuardianResponse> createGuardian(
      @RequestBody CreateGuardianRequest request) {
    GuardianResponse response = guardianService.createGuardian(request);
    return ResponseEntity.ok(response);
  }

  // Get a specific guardian by ID
  @GetMapping("/{guardianId}")
  public ResponseEntity<GuardianResponse> getGuardian(@PathVariable String guardianId) {
    GuardianResponse response = guardianService.getGuardian(guardianId);
    return ResponseEntity.ok(response);
  }

  // Update an existing guardian by ID
  @PutMapping("/{guardianId}")
  public ResponseEntity<GuardianResponse> updateGuardian(
      @PathVariable String guardianId, @RequestBody UpdateGuardianRequest request) {
    GuardianResponse response = guardianService.updateGuardian(guardianId, request);
    return ResponseEntity.ok(response);
  }

  // Delete a guardian by ID
  @DeleteMapping("/{guardianId}")
  public ResponseEntity<Void> deleteGuardian(@PathVariable String guardianId) {
    guardianService.deleteGuardian(guardianId);
    return ResponseEntity.noContent().build();
  }
}
