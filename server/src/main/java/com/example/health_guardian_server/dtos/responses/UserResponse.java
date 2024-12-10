package com.example.health_guardian_server.dtos.responses;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {
  String id;
  String username;
  String email;
  String avatar;
}
