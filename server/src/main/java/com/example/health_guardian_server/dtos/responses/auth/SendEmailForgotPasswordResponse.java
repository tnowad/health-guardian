package com.example.health_guardian_server.dtos.responses.auth;

public record SendEmailForgotPasswordResponse(String message, int retryAfter) {
}
