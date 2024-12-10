package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SurgeryRepository
    extends JpaRepository<Surgery, String>, JpaSpecificationExecutor<Surgery> {}
