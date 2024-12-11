package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

  @Mapping(source = "user.id", target = "userId")
  PrescriptionResponse toPrescriptionResponse(Prescription prescription);

  Prescription toPrescription(CreatePrescriptionRequest prescriptionRequest);
}
