package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.User;

public interface AccountService {
  AccountStatus checkAccountStatus(String accountId);

  String getUserIdByAccountId(String accountId);

  Account registerNewAccount();

  void updateAccountStatus(String accountId, AccountStatus status);

  Account createAccountWithLocalProvider(User user, LocalProvider localProvider);

  Account getAccountByUserId(String id);
}
