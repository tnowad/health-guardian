package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.ConsentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentFormRepository extends JpaRepository<ConsentForm, String> {}
