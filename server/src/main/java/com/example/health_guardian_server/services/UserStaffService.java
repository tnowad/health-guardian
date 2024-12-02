package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import org.springframework.data.domain.Page;

public interface UserStaffService {
  // Define methods

  Page<UserStaffResponse> getAllUserStaffs(ListUserStaffRequest request);

  UserStaffResponse getUserStaffById(String id);

  UserStaffResponse createUserStaff(CreateUserStaffRequest userStaff);

  UserStaffResponse updateUserStaff(UserStaffResponse userStaff);

  void deleteUserStaff(String id);

  UserStaffResponse updateUserStaff(String id, UpdateUserStaffRequest request);
}
