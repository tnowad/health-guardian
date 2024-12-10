package com.example.health_guardian_server.dtos.responses.user;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCurrentUserPermissionsResponse {
  String message;
  Set<String> items;
}
