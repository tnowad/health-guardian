package com.example.health_guardian_server.dtos.requests;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateUserStaffRequest {

  private String firstName;

  private String lastName;

  Date dateOfBirth;

  private String userId;

  private String role;

  private String roleType;

}
