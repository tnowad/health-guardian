package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserMedicalStaffResponse;
import org.springframework.data.domain.Page;

public interface UserMedicalStaffService {
  // Define methods

  Page<UserMedicalStaffResponse> getAllUserMedicalStaffs(ListUserMedicalStaffRequest request);

  UserMedicalStaffResponse getUserMedicalStaffById(String id);

  UserMedicalStaffResponse createUserMedicalStaff(CreateUserMedicalStaffRequest userMedicalStaff);

  UserMedicalStaffResponse updateUserMedicalStaff(String id, UpdateUserMedicalStaffRequest request);

  void deleteUserMedicalStaff(String id);
}
