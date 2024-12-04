package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.responses.UserResponse;
import com.example.health_guardian_server.entities.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse toUserResponse(User user);

  User toUser(UserResponse userResponse);
}
