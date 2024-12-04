package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreatePatientRequest;
import com.example.health_guardian_server.dtos.requests.UpdatePatientRequest;
import com.example.health_guardian_server.dtos.responses.PatientResponse;
import com.example.health_guardian_server.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
  @Mapping(target = "guardianId", source = "guardian.id")
  PatientResponse toPatientResponse(Patient patient);

  Patient toPatient(PatientResponse patientResponse);

  Patient toPatient(CreatePatientRequest createPatientRequest);

  Patient toPatient(UpdatePatientRequest updatePatientRequest);
}
