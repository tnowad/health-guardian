package com.example.health_guardian_server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.health_guardian_server.dtos.requests.prescription.CreatePrescriptionItemRequest;
import com.example.health_guardian_server.dtos.responses.prescription.PrescriptionItemResponse;
import com.example.health_guardian_server.entities.PrescriptionItem;

@Mapper(componentModel = "spring")
public interface PrescriptionItemMapper {

  PrescriptionItemResponse toPrescriptionItemResponse(PrescriptionItem prescriptionItem);

  PrescriptionItem toPrescriptionItem(CreatePrescriptionItemRequest prescriptionItemRequest);
}
