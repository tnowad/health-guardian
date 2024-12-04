package com.example.health_guardian_server.dtos.responses;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
  private String id;
  private String firstName;
  private String lastName;
  private Date dob;
  private String gender;
  private String guardianId;
  private String status;
  private Date createdAt;
  private Date updatedAt;
}
