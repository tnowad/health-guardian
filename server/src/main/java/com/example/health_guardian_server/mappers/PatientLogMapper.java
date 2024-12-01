package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.PatientLogResponse;
import com.example.health_guardian_server.entities.PatientLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientLogMapper {
  // Define methods

  PatientLogResponse toPatientLogResponse(PatientLog patientLog);

  PatientLog toPatientLog(PatientLogResponse patientLogResponse);
}
