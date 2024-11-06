package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.AggregatedSideEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatedSideEffectRepository
    extends JpaRepository<AggregatedSideEffect, String> {}
