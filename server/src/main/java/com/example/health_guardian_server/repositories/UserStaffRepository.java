package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.UserStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStaffRepository extends JpaRepository<UserStaff, String> {
  Page<UserStaff> findAll(Pageable pageable);
    List<UserStaff> findByUserId(String userId);

  List<UserStaff> findByRole(String role);

  List<UserStaff> findByRoleType(String roleType);
}
