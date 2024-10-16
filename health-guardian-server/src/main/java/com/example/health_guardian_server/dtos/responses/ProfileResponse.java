package com.example.health_guardian_server.dtos.responses;

import java.time.LocalDate;

import com.example.health_guardian_server.enums.Visibility;

public class ProfileResponse {
  String fullName;

  String bio;

  String phoneNunmber;

  String address;

  LocalDate dateOfBirth;

  String avatarUrl;

  Visibility visibility;

}
