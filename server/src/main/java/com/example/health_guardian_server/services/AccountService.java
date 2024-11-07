package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.AccountStatus;

public interface AccountService {
  AccountStatus checkAccountStatus(String accountId);

  String getUserIdByAccountId(String accountId);

  Account registerNewAccount();
}
