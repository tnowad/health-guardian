package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.mappers.AppointmentMapper;
import com.example.health_guardian_server.repositories.AppointmentRepository;
import com.example.health_guardian_server.services.AppointmentService;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.var;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
  AppointmentRepository appointmentRepository;

  AppointmentMapper appointmentMapper;



  @Override
  public Page<AppointmentResponse> getAllAppointments(ListAppointmentRequest request) {
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    AppointmentSpecification specification = new AppointmentSpecification(request);

    var appointments = appointmentRepository.findAll(specification, pageRequest).map(appointmentMapper::toAppointmentResponse);

    return appointments;
  }

  @Override

  public AppointmentResponse getAppointmentById(String appointmentId) {
    return appointmentRepository.findById(appointmentId)
      .map(appointmentMapper::toAppointmentResponse)
      .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId));

  }

  @Override
  public AppointmentResponse createAppointment(Appointment appointment){
    Appointment createdAppointment = appointmentRepository.save(appointment);
    return appointmentMapper.toAppointmentResponse(createdAppointment);
  }

  @Override

  public AppointmentResponse updateAppointment(String appointmentId, Appointment appointment) {
    Appointment existingAppointment = appointmentRepository.findById(appointmentId)
      .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId));

    existingAppointment.setAppointmentDate(appointment.getAppointmentDate());


    existingAppointment.setDoctor(appointment.getDoctor());
    existingAppointment.setPatient(appointment.getPatient());
    existingAppointment.setReasonForVisit(appointment.getReasonForVisit());
    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
    return appointmentMapper.toAppointmentResponse(updatedAppointment);
  }

  @Override
  public void deleteAppointment(String appointmentId) {

    Appointment existingAppointment = appointmentRepository.findById(appointmentId)
      .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId));

    appointmentRepository.delete(existingAppointment);
  }

  private void sendNotification(String title, String messageBody) {
    try {
      Message message =
          Message.builder()
              .setNotification(Notification.builder().setTitle(title).setBody(messageBody).build())
              .setTopic("appointments")
              .build();

      FirebaseMessaging.getInstance().send(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
