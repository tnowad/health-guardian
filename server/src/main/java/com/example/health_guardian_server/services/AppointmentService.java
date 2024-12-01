package com.example.health_guardian_server.services;

import com.example.health_guardian_server.dtos.requests.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
import com.example.health_guardian_server.entities.Appointment;

import org.springframework.data.domain.Page;


import java.util.List;

public interface AppointmentService {
  // Define methods


  Page<AppointmentResponse> getAllAppointments(ListAppointmentRequest request);

  AppointmentResponse getAppointmentById(String id);

  AppointmentResponse createAppointment(Appointment appointment);

  AppointmentResponse updateAppointment(String id, Appointment appointment);

  void deleteAppointment(String id);

}
