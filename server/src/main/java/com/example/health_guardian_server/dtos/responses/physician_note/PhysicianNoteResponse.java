package com.example.health_guardian_server.dtos.responses.physician_note;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicianNoteResponse {
  private String id;
  private String userId;
  private Date date;
  private String note;
}
