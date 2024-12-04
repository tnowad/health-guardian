package com.example.health_guardian_server.dtos.responses;

public record SendEmailForgotPasswordResponse(String message, int retryAfter) {
}
