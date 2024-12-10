package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AllergyRepository
    extends JpaRepository<Allergy, String>, JpaSpecificationExecutor<Allergy> {}
