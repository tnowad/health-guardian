package com.example.health_guardian_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.health_guardian_server.entities.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

  @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.id IN :roleIds")
  List<Permission> getPermissionsByRoleIds(List<String> roleIds);
}
