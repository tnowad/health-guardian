package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.SideEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SideEffectRepository
    extends JpaRepository<SideEffect, String>, JpaSpecificationExecutor<SideEffect> {
}
