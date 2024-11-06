package com.example.health_guardian_server.services;

import java.util.List;
import com.example.health_guardian_server.entities.Account;

public interface AccountService {

  Account findByEmail(String email);

  List<Account> findAll();

  void updatePassword(Account user, String password);

  Account update(Account account);

  boolean existsByEmail(String email);

  Account createaccount(Account account);

  void activateAccount(Account account);

  Account getCurrentAccount();
}
