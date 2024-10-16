package com.example.health_guardian_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.health_guardian_server.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, String> {
}
