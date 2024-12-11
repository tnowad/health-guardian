package com.example.health_guardian_server.dtos.responses.physician_note;

import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.entities.PhysicianNote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListPhysicianNotesResponse {
  List<PhysicianNote> items;
  String message;
}
