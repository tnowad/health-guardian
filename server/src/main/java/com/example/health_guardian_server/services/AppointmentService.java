package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.appointment.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.UpdateAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.entities.Appointment;
import org.springframework.data.domain.Page;

public interface AppointmentService {

  Page<AppointmentResponse> getAllAppointments(ListAppointmentRequest request);

  AppointmentResponse getAppointmentById(String id);

  AppointmentResponse createAppointment(CreateAppointmentRequest createAppointment);

  AppointmentResponse updateAppointment(String id, UpdateAppointmentRequest request);

  Appointment deleteAppointment(String id);
}
