package com.example.health_guardian_server.dtos.others;

import lombok.Getter;

public record Tokens(String accessToken, String refreshToken) {
}
