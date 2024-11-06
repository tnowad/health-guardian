package com.example.health_guardian_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.health_guardian_server.entities.UserStaff;
import java.util.UUID;

@Repository
public interface UserStaffRepository extends JpaRepository<UserStaff, UUID> {
}

