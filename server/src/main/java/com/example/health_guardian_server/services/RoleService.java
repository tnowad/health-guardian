package com.example.health_guardian_server.services;

import java.util.List;

import com.example.health_guardian_server.entities.Role;

public interface RoleService {
  List<Role> getRolesByUserId(String userId);
}
