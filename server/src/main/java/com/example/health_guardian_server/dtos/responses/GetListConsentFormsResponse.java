package com.example.health_guardian_server.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListConsentFormsResponse {
  List<ConsentFormResponse> items;
  String message;
}
