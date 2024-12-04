package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.CreateUserStaffRequest;
import com.example.health_guardian_server.dtos.requests.UpdateUserStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserStaffResponse;
import com.example.health_guardian_server.entities.UserStaff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserStaffMapper {

  @Mapping(target = "userId", source = "user.id")
  UserStaffResponse toUserStaffResponse(UserStaff userStaff);

  @Mapping(target = "user.id", source = "userId")
  UserStaff toUserStaff(UserStaffResponse userStaffResponse);

  @Mapping(target = "user.id", source = "userId")
  @Mapping(target = "id", ignore = true)
  UserStaff toUserStaff(UpdateUserStaffRequest createUserStaffRequest);

  UserStaff toUserStaff(CreateUserStaffRequest createUserStaffRequest);
}
