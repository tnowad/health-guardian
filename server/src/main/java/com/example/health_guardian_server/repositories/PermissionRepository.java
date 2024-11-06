package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Permission;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

  @Query("SELECT p.id FROM Permission p JOIN p.roles r WHERE r.id IN :roleIds")
  Set<String> getPermissionIdsByRoleIds(Set<String> roleIds);

  @Query("SELECT p.name FROM Permission p JOIN p.roles r WHERE r.id IN :roleIds")
  Set<String> getPermissionNamesByRoleIds(Set<String> roleIds);
}
