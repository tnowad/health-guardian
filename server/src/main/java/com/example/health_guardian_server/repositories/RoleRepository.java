package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  @Query("SELECT r.id FROM Role r JOIN r.users u WHERE u.id = :userId")
  Set<String> findRoleIdsByUserId(String userId);

  @Query("SELECT r FROM Role r WHERE r.id IN :roleIds")
  Set<Role> findAllByIds(Set<String> roleIds);
}
