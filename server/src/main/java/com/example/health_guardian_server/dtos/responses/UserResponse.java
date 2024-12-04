package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.UserType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {
  String id;
  String username;
  String email;
  UserType type;
  String avatar;
}
