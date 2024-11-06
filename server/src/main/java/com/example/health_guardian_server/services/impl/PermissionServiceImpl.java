package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Permission;
import com.example.health_guardian_server.entities.Role;
import com.example.health_guardian_server.repositories.PermissionRepository;
import com.example.health_guardian_server.services.PermissionService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

  PermissionRepository permissionRepository;

  @Override
  public List<Permission> getPermissionsByRoles(List<Role> roles) {
    var roleIds = roles.stream().map(Role::getId).toList();
    return permissionRepository.getPermissionsByRoleIds(roleIds);
  }
}
