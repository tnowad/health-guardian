package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.Patient;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientLogResponse {
  private String id;
  private Patient patient;
  private String logType;
  private List<String> fileUrls;
  private String message;
  private Date createdAt;
}
