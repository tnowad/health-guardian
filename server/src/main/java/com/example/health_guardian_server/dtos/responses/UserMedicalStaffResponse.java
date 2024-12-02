package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.Hospital;
import com.example.health_guardian_server.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicalStaffResponse {
  private String id;
  private User user;
  private Hospital hospital;
  private String staffType;
  private String specialization;
  private String role;
  private  boolean active;
  private Date endDate;

}
