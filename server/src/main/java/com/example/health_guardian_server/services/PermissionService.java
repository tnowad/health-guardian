package com.example.health_guardian_server.services;

import java.util.List;

import com.example.health_guardian_server.entities.Permission;
import com.example.health_guardian_server.entities.Role;

public interface PermissionService {
  List<Permission> getPermissionsByRoles(List<Role> roles);
}
