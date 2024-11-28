package com.example.health_guardian_server.services;

import com.example.health_guardian_server.entities.Appointment;

import java.util.List;

public interface AppointmentService {
  // Define methods
  List<Appointment> getAppointments();
  Appointment createAppointment(Appointment appointment);
  Appointment getAppointmentById(String appointmentId);

  Appointment updateAppointment(Appointment appointment);

  void deleteAppointment(String appointmentId);

}
