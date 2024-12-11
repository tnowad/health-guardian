package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.requests.prescription.ListPrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import com.example.health_guardian_server.mappers.PrescriptionMapper;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;

  private final UserRepository userRepository;

  private final PrescriptionMapper prescriptionMapper;

  @Override
  public Page<PrescriptionResponse> getAllPrescriptions(ListPrescriptionRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllPrescriptions'");
  }

  @Override
  public Prescription getPrescriptionById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPrescriptionById'");
  }

  @Override
  public Prescription createPrescription(CreatePrescriptionRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createPrescription'");
  }

  @Override
  public Prescription updatePrescription(Prescription prescription) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updatePrescription'");
  }

  @Override
  public void deletePrescription(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deletePrescription'");
  }
}
