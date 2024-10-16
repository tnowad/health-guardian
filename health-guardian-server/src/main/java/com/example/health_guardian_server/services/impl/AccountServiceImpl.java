package com.example.health_guardian_server.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.services.AccountService;

import jakarta.transaction.Transactional;

import com.example.health_guardian_server.exceptions.AuthException;
import com.example.health_guardian_server.exceptions.errorcodes.AuthenticationErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountServiceImpl implements AccountService {
  AccountRepository accountRepository;

  @Override
  public Account findByEmail(String email) {
    return accountRepository.findByEmail(email);
  }

  @Override
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  @Transactional
  public void updatePassword(Account account, String password) {
    account.setPassword(password);
    accountRepository.save(account);
  }

  @Override
  public Account update(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public boolean existsByEmail(String email) {
    return accountRepository.existsByEmail(email);
  }

  @Override
  public Account createaccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public void activateAccount(Account account) {
    account.setActivated(true);

  }
}
