package com.example.health_guardian_server.dtos.responses;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FullAccountInfoResponse {
    AccountInfoResponse accountInfoResponse;
    List<RoleResponse> roles;
    List<PermissionResponse> permissions;
}
