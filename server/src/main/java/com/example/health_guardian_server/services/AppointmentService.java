package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
import org.springframework.data.domain.Page;

public interface AppointmentService {

  Page<AppointmentResponse> getAllAppointments(ListAppointmentRequest request);

  AppointmentResponse getAppointmentById(String id);

  AppointmentResponse createAppointment(CreateAppointmentRequest createAppointment);

  AppointmentResponse updateAppointment(String id, CreateAppointmentRequest createAppointment);

  void deleteAppointment(String id);
}
