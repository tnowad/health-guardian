package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.services.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j  // Enable logging for this class
public class AccountServiceImpl implements AccountService {

  AccountRepository accountRepository;

  @Override
  public AccountStatus checkAccountStatus(String accountId) {
    log.debug("Checking status for accountId: {}", accountId);
    var account = accountRepository.findById(accountId).orElse(null);
    if (account == null) {
      log.warn("Account not found for accountId: {}", accountId);
      return null;
    }
    log.info("Account status for accountId {}: {}", accountId, account.getStatus());
    return account.getStatus();
  }

  @Override
  public Account registerNewAccount() {
    log.warn("Attempted to call unsupported method 'registerNewAccount'");
    throw new UnsupportedOperationException("Unimplemented method 'registerNewAccount'");
  }

  @Override
  public String getUserIdByAccountId(String accountId) {
    log.debug("Fetching userId for accountId: {}", accountId);
    String userId = accountRepository.findUserIdByAccountId(accountId);
    if (userId == null) {
      log.warn("No userId found for accountId: {}", accountId);
    } else {
      log.info("Found userId {} for accountId {}", userId, accountId);
    }
    return userId;
  }

  @Override
  public void updateAccountStatus(String accountId, AccountStatus status) {
    log.debug("Updating account status for accountId: {} to {}", accountId, status);
    var account = accountRepository.findById(accountId).orElse(null);
    if (account == null) {
      log.error("Account not found for accountId: {}", accountId);
      throw new IllegalArgumentException("Account not found");
    }
    account.setStatus(status);
    accountRepository.save(account);
    log.info("Updated account status for accountId: {} to {}", accountId, status);
  }

  @Override
  public Account createAccountWithLocalProvider(User user, LocalProvider localProvider) {
    log.debug("Creating new account for userId: {} with localProvider: {}", user.getId(), localProvider.getId());
    var account =
      Account.builder()
        .status(AccountStatus.INACTIVE)
        .user(user)
        .localProviders(Set.of(localProvider))
        .build();
    accountRepository.save(account);
    log.info("Successfully created new account with id: {}", account.getId());
    return account;
  }

  @Override
  public Account getAccountByUserId(String id) {
    log.debug("Fetching account for userId: {}", id);
    var accounts = accountRepository.findByUserId(id);
    if (accounts.isEmpty()) {
      log.warn("No account found for userId: {}", id);
      return null;
    }
    log.info("Found account for userId: {}", id);
    return accounts.get(0);
  }
}
