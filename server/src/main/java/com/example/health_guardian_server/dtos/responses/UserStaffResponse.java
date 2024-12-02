package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.User;
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
  private User user;
  private String role;
  private String roleType;
}
