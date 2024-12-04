package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.entities.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrentUserInfomationResponse {
  String userId;
  String name;
  String username;
  String email;
  String avatarUrl;
  UserType role;
  StaffInforResponse staff;
  MedicalStaffInforResponse medicalStaff;
  PatientInforResponse patient;
}
