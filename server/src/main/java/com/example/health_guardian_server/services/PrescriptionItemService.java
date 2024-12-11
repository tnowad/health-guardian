package com.example.health_guardian_server.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionItemRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionItemRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionItemResponse;
import com.example.health_guardian_server.entities.PrescriptionItem;

public interface PrescriptionItemService {

  Page<PrescriptionItemResponse> getAllPrescriptionItems(ListPrescriptionItemRequest request);

  PrescriptionItem getPrescriptionItemById(String id);

  PrescriptionItem createPrescriptionItem(CreatePrescriptionItemRequest request);

  void deletePrescriptionItem(String id);

  PrescriptionItem updatePrescriptionItem(String prescriptionId, CreatePrescriptionItemRequest request);
}
