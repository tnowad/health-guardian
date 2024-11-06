package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.repositories.PermissionRepository;
import com.example.health_guardian_server.services.PermissionService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

  PermissionRepository permissionRepository;

  @Override
  public Set<String> getPermissionIdsByRoleIds(Set<String> roleIds) {
    return permissionRepository.getPermissionIdsByRoleIds(roleIds);
  }
}
