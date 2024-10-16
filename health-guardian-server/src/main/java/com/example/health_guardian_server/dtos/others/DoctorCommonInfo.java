package com.example.health_guardian_server.dtos.others;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorCommonInfo {
    @NotNull(message = "Name must not null")
    String name;
    String email;
    String phoneNumber;
}
