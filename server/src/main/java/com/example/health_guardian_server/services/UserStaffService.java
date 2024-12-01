package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.ListUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.GetListUserStaffResponse;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.UserStaff;
import com.example.health_guardian_server.entities.UserType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserStaffService {
  // Define methods

  Page<UserStaffResponse> getAllUserStaffs(ListUserStaffRequest request);

  UserStaffResponse getUserStaffById(String id);

  UserStaffResponse createUserStaff(CreateUserStaffRequest userStaff);

  UserStaffResponse updateUserStaff( UserStaffResponse userStaff);

  void deleteUserStaff(String id);


}
