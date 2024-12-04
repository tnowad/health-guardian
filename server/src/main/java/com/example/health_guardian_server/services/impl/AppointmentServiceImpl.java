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
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;

  @Override
  public Page<AppointmentResponse> getAllAppointments(ListAppointmentRequest request) {
    log.debug("Fetching all appointments with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    AppointmentSpecification specification = new AppointmentSpecification(request);

    var appointments = appointmentRepository
        .findAll(specification, pageRequest)
        .map(appointmentMapper::toResponse);

    log.info("Fetched {} appointments", appointments.getTotalElements());
    return appointments;
  }

  @Override
  public AppointmentResponse getAppointmentById(String appointmentId) {
    log.debug("Fetching appointment with id: {}", appointmentId);
    return appointmentRepository
        .findById(appointmentId)
        .map(appointmentMapper::toAppointmentResponse)
        .orElseThrow(
            () -> {
              log.error("Appointment not found with id: {}", appointmentId);
              return new ResourceNotFoundException(
                  "Appointment not found with id " + appointmentId);
            });
  }

  @Override
  public AppointmentResponse createAppointment(CreateAppointmentRequest createAppointmentRequest) {
    log.debug("Creating appointment: {}", createAppointmentRequest);
    Appointment createdAppointment = appointmentRepository
        .save(appointmentMapper.toAppointment(createAppointmentRequest));
    log.info("Appointment created with id: {}", createdAppointment.getId());
    return appointmentMapper.toAppointmentResponse(createdAppointment);
  }

  @Override
  public AppointmentResponse updateAppointment(
      String appointmentId, CreateAppointmentRequest appointment) {
    log.debug("Updating appointment with id: {}", appointmentId);
    Appointment existingAppointment = appointmentRepository
        .findById(appointmentId)
        .orElseThrow(
            () -> {
              log.error("Appointment not found with id: {}", appointmentId);
              return new ResourceNotFoundException(
                  "Appointment not found with id " + appointmentId);
            });
    existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
    existingAppointment.setReasonForVisit(appointment.getReasonForVisit());
    existingAppointment.setStatus(appointment.getStatus());
    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
    log.info("Appointment updated with id: {}", updatedAppointment.getId());
    return appointmentMapper.toAppointmentResponse(updatedAppointment);
  }

  @Override
  public void deleteAppointment(String appointmentId) {
    log.debug("Deleting appointment with id: {}", appointmentId);
    Appointment existingAppointment = appointmentRepository
        .findById(appointmentId)
        .orElseThrow(
            () -> {
              log.error("Appointment not found with id: {}", appointmentId);
              return new ResourceNotFoundException(
                  "Appointment not found with id " + appointmentId);
            });

    appointmentRepository.delete(existingAppointment);
    log.info("Appointment deleted with id: {}", appointmentId);
  }

  private void sendNotification(String title, String messageBody) {
    log.debug("Sending notification with title: '{}' and message: '{}'", title, messageBody);
    try {
      Message message = Message.builder()
          .setNotification(Notification.builder().setTitle(title).setBody(messageBody).build())
          .setTopic("appointments")
          .build();

      FirebaseMessaging.getInstance().send(message);
      log.info("Notification sent with title: '{}' and message: '{}'", title, messageBody);
    } catch (Exception e) {
      log.error("Failed to send notification", e);
    }
  }
}
