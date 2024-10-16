package com.example.health_guardian_server.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignOutRequest(

    @NotNull(message = "null_token") @NotBlank(message = "blank_token") String accessToken,

    @NotNull(message = "null_token") @NotBlank(message = "blank_token") String refreshToken

) {
}
