package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> , JpaSpecificationExecutor<Appointment> {}
