package com.example.health_guardian_server.repositories;

import com.example.health_guardian_server.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  Optional<User> findUserByEmail(String email);
  // @Query("SELECT u FROM User u JOIN u.accounts a WHERE a.id = :accountId")
  // User findByAccountId(String accountId);
}
