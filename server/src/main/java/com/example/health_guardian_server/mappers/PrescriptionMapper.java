package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

  @Mapping(source = "patient.id", target = "patientId")
  @Mapping(source = "medication.id", target = "medicationId")
  @Mapping(source = "prescribedBy.id", target = "prescribedById")
  PrescriptionResponse toPrescriptionResponse(Prescription prescription);

  Prescription toPrescription(PrescriptionResponse prescriptionResponse);
}
