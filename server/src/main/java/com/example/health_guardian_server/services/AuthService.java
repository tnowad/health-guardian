package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.SignInRequest;
import com.example.health_guardian_server.dtos.responses.SignInResponse;

public interface AuthService {
  SignInResponse signIn(SignInRequest request);
}
