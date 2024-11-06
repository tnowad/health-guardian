package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  AccountRepository accountRepository;

  @Override
  public AccountStatus checkAccountStatus(String accountId) {
    return accountRepository.findById(accountId).get().getStatus();
  }

  @Override
  public Account registerNewAccount() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'registerNewAccount'");
  }
}
