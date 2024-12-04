package com.example.health_guardian_server.dtos.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStaffResponse {

  private String id;

  String firstName;
  String lastName;

  Date dateOfBirth;

  private String userId;

  private String role;
  private String roleType;
}
