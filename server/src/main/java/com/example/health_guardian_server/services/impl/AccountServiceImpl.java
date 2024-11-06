package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.services.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  @Override
  public Account findAccountByUserId(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAccountByUserId'");
  }

  @Override
  public AccountStatus checkAccountStatus(String accountId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'checkAccountStatus'");
  }

  @Override
  public Account registerNewAccount() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'registerNewAccount'");
  }
}
