package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.GetListUserStaffResponse;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.UserStaff;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserStaffService {
  // Define methods

  Page<UserStaffResponse> getAllUserStaffs(int page, int size);

  UserStaffResponse getUserStaffById(String id);

  UserStaffResponse createUserStaff(CreateUserStaffRequest createUserStaffRequest);

  UserStaffResponse updateUserStaff(String id, UserStaffResponse userStaff);

  void deleteUserStaff(String id);

}
