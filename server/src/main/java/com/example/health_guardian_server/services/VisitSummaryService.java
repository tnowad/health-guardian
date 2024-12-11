package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.appointment.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.UpdateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.CreateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.UpdateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.visit_summary.VisitSummaryResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.VisitSummary;
import org.springframework.data.domain.Page;

public interface VisitSummaryService {
  Page<VisitSummaryResponse> getAllVisitSummaries(ListVisitSummaryRequest request);

  VisitSummaryResponse getVisitSummaryById(String id);

  VisitSummaryResponse createVisitSummary(CreateVisitSummaryRequest request);

  VisitSummaryResponse updateVisitSummary(String id, UpdateVisitSummaryRequest request);

  SimpleResponse deleteVisitSummary(String id);
}
