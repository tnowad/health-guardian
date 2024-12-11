package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionItemRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionItemRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionItemResponse;
import com.example.health_guardian_server.entities.PrescriptionItem;
import com.example.health_guardian_server.mappers.PrescriptionItemMapper;
import com.example.health_guardian_server.services.PrescriptionItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/prescription-items")
@RequiredArgsConstructor
public class PrescriptionItemController {
  private final PrescriptionItemService prescriptionItemService;
  private final PrescriptionItemMapper prescriptionItemMapper;

  @GetMapping
  public ResponseEntity<Page<PrescriptionItemResponse>> getAllPrescriptionItems(
      @ModelAttribute ListPrescriptionItemRequest request) {
    return new ResponseEntity<>(
        prescriptionItemService.getAllPrescriptionItems(request), HttpStatus.OK);
  }

  @GetMapping("/{prescriptionItemId}")
  public ResponseEntity<PrescriptionItemResponse> getPrescriptionItemById(@PathVariable String id) {
    PrescriptionItem prescriptionItem = prescriptionItemService.getPrescriptionItemById(id);
    return new ResponseEntity<>(
        prescriptionItemMapper.toPrescriptionItemResponse(prescriptionItem), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PrescriptionItemResponse> createPrescriptionItem(
      @RequestBody CreatePrescriptionItemRequest request) {
    PrescriptionItem prescriptionItem = prescriptionItemService.createPrescriptionItem(request);
    PrescriptionItemResponse prescriptionItemResponse = prescriptionItemMapper
        .toPrescriptionItemResponse(prescriptionItem);
    return new ResponseEntity<>(prescriptionItemResponse, HttpStatus.OK);
  }

  @PutMapping("/{prescriptionItemId}")
  public ResponseEntity<PrescriptionItemResponse> updatePrescriptionItem(
      @PathVariable String prescriptionItemId, @RequestBody CreatePrescriptionItemRequest request) {
    PrescriptionItem prescriptionItem = prescriptionItemService.updatePrescriptionItem(prescriptionItemId, request);
    PrescriptionItemResponse prescriptionItemResponse = prescriptionItemMapper
        .toPrescriptionItemResponse(prescriptionItem);
    return new ResponseEntity<>(prescriptionItemResponse, HttpStatus.OK);
  }

  @DeleteMapping("/{prescriptionItemId}")
  public ResponseEntity<Void> deletePrescriptionItem(@PathVariable String prescriptionItemId) {
    prescriptionItemService.deletePrescriptionItem(prescriptionItemId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
