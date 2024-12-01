package com.example.health_guardian_server.mappers;


import com.example.health_guardian_server.dtos.responses.PatientResponse;
import com.example.health_guardian_server.entities.Patient;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface PatientMapper {
    // Define methods
  PatientResponse toPatientResponse(Patient patient);
  Patient toPatient(PatientResponse patientResponse);

}
