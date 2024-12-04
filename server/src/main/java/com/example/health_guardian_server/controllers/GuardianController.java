package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateGuardianRequest;
import com.example.health_guardian_server.dtos.requests.ListGuardiansRequest;
import com.example.health_guardian_server.dtos.responses.GuardianResponse;
import com.example.health_guardian_server.services.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guardians")
public class GuardianController {
  private final GuardianService guardianService;

  @GetMapping
  public ResponseEntity<Page<GuardianResponse>> getAllGuardians(
      @ModelAttribute ListGuardiansRequest request) {
    Page<GuardianResponse> guardians = guardianService.getAllGuardians(request);

    return new ResponseEntity<>(guardians, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<GuardianResponse> getGuardianById(String id) {
    GuardianResponse guardian = guardianService.getGuardianById(id);
    return new ResponseEntity<>(guardian, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<GuardianResponse> createGuardian(
      @RequestBody CreateGuardianRequest createGuardianRequest) {
    GuardianResponse createdGuardian = guardianService.createGuardian(createGuardianRequest);
    return new ResponseEntity<>(createdGuardian, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<GuardianResponse> updateGuardian(
      String id, CreateGuardianRequest createGuardianRequest) {
    GuardianResponse updatedGuardian = guardianService.updateGuardian(id, createGuardianRequest);
    return new ResponseEntity<>(updatedGuardian, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGuardian(String id) {
    guardianService.deleteGuardian(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
