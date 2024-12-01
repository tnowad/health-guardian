package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.UserStaff;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStaffMapper {
  UserStaffResponse toUserStaffResponse(UserStaff userStaff);

  UserStaff toUserStaff(UserStaffResponse userStaffResponse);
}