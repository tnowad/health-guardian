package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.repositories.PermissionRepository;
import com.example.health_guardian_server.services.PermissionService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionServiceImpl implements PermissionService {

  PermissionRepository permissionRepository;

  @Override
  public Set<String> getPermissionIdsByRoleIds(Set<String> roleIds) {
    return permissionRepository.getPermissionIdsByRoleIds(roleIds);
  }
}
