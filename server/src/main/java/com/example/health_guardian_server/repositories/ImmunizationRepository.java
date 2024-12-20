package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Immunization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmunizationRepository
    extends JpaRepository<Immunization, String>, JpaSpecificationExecutor<Immunization> {}
