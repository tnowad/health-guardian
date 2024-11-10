package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.LocalProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalProviderRepository extends JpaRepository<LocalProvider, String> {
   LocalProvider findByEmail(String email);
}
