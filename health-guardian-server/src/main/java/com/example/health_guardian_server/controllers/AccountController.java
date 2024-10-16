package com.example.health_guardian_server.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.dtos.responses.AccountInfoResponse;
import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.mappers.AccountMapper;
import com.example.health_guardian_server.services.AccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Account Apis")
public class AccountController {
  AccountService accountService;
  AccountMapper accountMapper;

  @GetMapping("/find-all")
  public ResponseEntity<List<AccountInfoResponse>> getAll() {
    return ResponseEntity.status(OK)
        .body(accountService.findAll().stream().map(accountMapper::toAccountInfoResponse).toList());
  }

}
