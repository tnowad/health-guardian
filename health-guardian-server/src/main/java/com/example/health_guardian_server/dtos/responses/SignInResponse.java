package com.example.health_guardian_server.dtos.responses;

import com.example.health_guardian_server.dtos.others.Tokens;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
  Tokens tokens;
  AccountInfoResponse accountInfoResponse;
}
