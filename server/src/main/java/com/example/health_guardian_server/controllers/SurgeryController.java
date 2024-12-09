package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateSurgeryRequest;
import com.example.health_guardian_server.dtos.requests.ListSurgeriesRequest;
import com.example.health_guardian_server.dtos.responses.SurgeryResponse;
import com.example.health_guardian_server.services.SurgeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surgeries")
@RequiredArgsConstructor
public class SurgeryController {
  private final SurgeryService surgeryService;

  @GetMapping
  public ResponseEntity<Page<SurgeryResponse>> listSurgeries(ListSurgeriesRequest request) {
    Page<SurgeryResponse> surgeries = surgeryService.getAllSurgeries(request);
    return ResponseEntity.ok(surgeries);
  }
  @PostMapping
  public ResponseEntity<SurgeryResponse> createSurgery(CreateSurgeryRequest request) {
    SurgeryResponse surgery = surgeryService.createSurgery(request);
    return ResponseEntity.ok(surgery);
  }
  @GetMapping("/{id}")
  public ResponseEntity<SurgeryResponse> getSurgery(String id) {
    SurgeryResponse surgery = surgeryService.getSurgeryById(id);
    return ResponseEntity.ok(surgery);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SurgeryResponse> updateSurgery(String id, CreateSurgeryRequest request) {
    SurgeryResponse surgery = surgeryService.updateSurgery(id, request);
    return ResponseEntity.ok(surgery);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSurgery(String id) {
    surgeryService.deleteSurgery(id);
    return ResponseEntity.noContent().build();
  }
}
