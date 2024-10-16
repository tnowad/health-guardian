package com.example.health_guardian_server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Verification;
import com.example.health_guardian_server.enums.VerificationType;

public interface VerificationRepository extends JpaRepository<Verification, String> {
  Optional<Verification> findByToken(String token);

  Optional<Verification> findByCode(String code);

  Optional<Verification> findByCodeAndVerificationType(String code, VerificationType verificationType);

  List<Verification> findByAccountAndVerificationType(Account account, VerificationType verificationType);

  void deleteByAccount_IdAndVerificationType(String accountId, VerificationType verificationType);

  void deleteByAccount_Id(String accountId);
}
