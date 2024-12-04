package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.repositories.PermissionRepository;
import com.example.health_guardian_server.services.PermissionService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j // Add the Slf4j annotation to enable logging
public class PermissionServiceImpl implements PermissionService {

  PermissionRepository permissionRepository;

  @Override
  public Set<String> getPermissionIdsByRoleIds(Set<String> roleIds) {
    log.debug("Fetching permission IDs for role IDs: {}", roleIds);
    Set<String> permissionIds = permissionRepository.getPermissionIdsByRoleIds(roleIds);
    log.info("Fetched {} permission IDs for {} role(s)", permissionIds.size(), roleIds.size());
    return permissionIds;
  }

  @Override
  public Set<String> getPermissionNamesByRoleIds(Set<String> roleIds) {
    log.debug("Fetching permission names for role IDs: {}", roleIds);
    Set<String> permissionNames = permissionRepository.getPermissionNamesByRoleIds(roleIds);
    log.info("Fetched {} permission name(s) for {} role(s)", permissionNames.size(), roleIds.size());
    return permissionNames;
  }
}
