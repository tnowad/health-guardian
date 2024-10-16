package com.example.health_guardian_server.dtos.responses;

import java.util.Date;

public record SendEmailForgotPasswordResponse(

    String message,

    Date retryAfter

) {

}
