package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.repositories.AppointmentRepository;
import com.example.health_guardian_server.services.AppointmentService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class AppointmentServiceImpl implements AppointmentService {
  AppointmentRepository appointmentRepository;


  public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public List<Appointment> getAppointments() {
    return (List<Appointment>) appointmentRepository.findAll();
  }

  @Override
  public Appointment createAppointment(Appointment appointment) {
    Appointment savedAppointment = appointmentRepository.save(appointment);
    sendNotification("New Appointment Created", "Appointment with ID: " + savedAppointment.getId() + " has been created.");
    return savedAppointment;
  }

  @Override
  public Appointment getAppointmentById(String appointmentId) {
    return appointmentRepository.findById(appointmentId).orElse(null);
  }

  @Override
  public Appointment updateAppointment(Appointment appointment) {


    Appointment updatedAppointment = appointmentRepository.save(appointment);
    sendNotification("Appointment Updated", "Appointment with ID: " + updatedAppointment.getId() + " has been updated.");

    return updatedAppointment;
  }

  @Override
  public void deleteAppointment(String appointmentId) {
    Appointment appointment = appointmentRepository.findById(appointmentId)
      .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId));

    appointmentRepository.delete(appointment);
    sendNotification("Appointment Deleted", "Appointment with ID: " + appointmentId + " has been deleted.");
  }


  private void sendNotification(String title, String messageBody) {
    try {
      Message message = Message.builder()
        .setNotification(Notification.builder()
          .setTitle(title)
          .setBody(messageBody)
          .build())
        .setTopic("appointments")
        .build();

      FirebaseMessaging.getInstance().send(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
