package com.example.health_guardian_server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.health_guardian_server.dtos.requests.CreateUserMedicalStaffRequest;
import com.example.health_guardian_server.dtos.responses.UserMedicalStaffResponse;
import com.example.health_guardian_server.entities.UserMedicalStaff;

@Mapper(componentModel = "spring")
public interface UserMedicalStaffMapper {

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "hospitalId", target = "hospital.id")
  @Mapping(target = "id", ignore = true)
  UserMedicalStaff toUserMedicalStaff(CreateUserMedicalStaffRequest createUserMedicalStaffRequest);

  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "hospital.id", target = "hospitalId")
  UserMedicalStaffResponse toUserMedicalStaffResponse(UserMedicalStaff userMedicalStaff);

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "hospitalId", target = "hospital.id")
  UserMedicalStaff toUserMedicalStaff(UserMedicalStaffResponse userMedicalStaffResponse);
}
