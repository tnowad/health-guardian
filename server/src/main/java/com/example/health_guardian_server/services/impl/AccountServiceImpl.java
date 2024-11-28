package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.AccountStatus;
import com.example.health_guardian_server.entities.LocalProvider;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.repositories.AccountRepository;
import com.example.health_guardian_server.services.AccountService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

  AccountRepository accountRepository;

  @Override
  public AccountStatus checkAccountStatus(String accountId) {
    return accountRepository.findById(accountId).get().getStatus();
  }

  @Override
  public Account registerNewAccount() {
    throw new UnsupportedOperationException("Unimplemented method 'registerNewAccount'");
  }

  @Override
  public String getUserIdByAccountId(String accountId) {
    return accountRepository.findUserIdByAccountId(accountId);
  }

  @Override
  public void updateAccountStatus(String accountId, AccountStatus status) {
    var account = accountRepository.findById(accountId).get();
    account.setStatus(status);
    accountRepository.save(account);
  }

  @Override
  public Account createAccountWithLocalProvider(User user, LocalProvider localProvider) {
    var account =
        Account.builder()
            .status(AccountStatus.ACTIVE)
            .user(user)
            .localProviders(Set.of(localProvider))
            .build();
    accountRepository.save(account);
    return account;
  }

  @Override
  public Account getAccountByUserId(String id) {
    return accountRepository.findByUserId(id).get(0);
  }
}
