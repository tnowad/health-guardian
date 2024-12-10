package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.PrescriptionResponse;
import com.example.health_guardian_server.entities.Prescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

  PrescriptionResponse toPrescriptionResponse(Prescription prescription);

  Prescription toPrescription(PrescriptionResponse prescriptionResponse);
}
