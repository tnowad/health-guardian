package com.example.health_guardian_server.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.health_guardian_server.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
  Set<Role> findRolesByUserId(@Param("userId") String userId);

}
