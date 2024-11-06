package com.example.health_guardian_server.services;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;

public interface PermissionService {
  @Query("SELECT p.id FROM Permission p WHERE p.roleId IN :roleIds")
  Set<String> getPermissionIdsByRoleIds(Set<String> roleIds);
}
