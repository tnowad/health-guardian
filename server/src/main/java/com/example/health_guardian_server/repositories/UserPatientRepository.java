package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPatientRepository extends JpaRepository<UserPatient, String> {}
