package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
  @Query("SELECT a.userId FROM Account a WHERE a.id = :accountId")
  String findUserIdByAccountId(String accountId);

  List<Account> findByUserId(String userId);
}
