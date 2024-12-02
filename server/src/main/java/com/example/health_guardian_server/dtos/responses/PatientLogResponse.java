package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientLogResponse {
  private String id;
  private Patient patient;
  private String logType;
  private String message;
  private Date createdAt;
}
