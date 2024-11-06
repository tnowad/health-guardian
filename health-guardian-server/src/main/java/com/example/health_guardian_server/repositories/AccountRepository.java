package com.example.health_guardian_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.health_guardian_server.entities.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
  Optional<Account> findByEmail(String email);

  boolean existsByEmail(String email);
}
