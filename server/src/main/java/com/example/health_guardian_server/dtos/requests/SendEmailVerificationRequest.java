package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.dtos.enums.VerificationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendEmailVerificationRequest(
    @NotNull(message = "null_field") @NotBlank(message = "blank_field") @Email(message = "invalid_email") String email)

    {
}
