package com.example.health_guardian_server.services;

import java.util.Set;

public interface RoleService {
  Set<String> getRoleIdsByUserId(String userId);

  Set<String> getDefaultRoleIds();

  Set<String> getDefaultRoleIdsForPatient();

  void assignRolesToUser(String userId, Set<String> roleIds);
}
