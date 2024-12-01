package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.Guardian;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
  private Guardian guardian;
  private String status;
  private Date createdAt;
  private Date updatedAt;
}
