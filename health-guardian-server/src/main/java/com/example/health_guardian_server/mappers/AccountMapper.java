package com.example.health_guardian_server.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.health_guardian_server.dtos.requests.SignUpRequest;
import com.example.health_guardian_server.dtos.responses.AccountInfoResponse;
import com.example.health_guardian_server.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  AccountInfoResponse toAccountInfoResponse(Account account);

  Account toAccount(SignUpRequest signUpRequest);
}
