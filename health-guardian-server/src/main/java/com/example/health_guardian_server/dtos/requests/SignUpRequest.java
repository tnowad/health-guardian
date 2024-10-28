package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter

public class SignUpRequest {

  @NotNull(message = "null_name")
  @NotBlank(message = "blank_name")
  String userName;

  @NotNull(message = "null_email")
  @NotBlank(message = "blank_email")
  @Email(message = "invalid_email")
  String email;

  @NotNull(message = "null_password")
  @NotBlank(message = "blank_password")
  @Size(min = 6, max = 20, message = "size_password")
  String password;

  @NotNull(message = "null_password")
  @NotBlank(message = "blank_password")
  @Size(min = 6, max = 20, message = "size_password")
  String passwordConfirmation;

  @NotNull(message = "null_accept_terms")
  Boolean acceptTerms;
}
