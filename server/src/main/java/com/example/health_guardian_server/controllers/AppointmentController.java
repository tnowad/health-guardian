package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.appointment.CreateAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.appointment.UpdateAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.appointment.AppointmentResponse;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.mappers.AppointmentMapper;
import com.example.health_guardian_server.services.AppointmentService;
import com.example.health_guardian_server.services.NotificationService;
import com.example.health_guardian_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

  private final AppointmentService appointmentService;

  @GetMapping
  public ResponseEntity<Page<AppointmentResponse>> getAllAppointments(
    @ModelAttribute ListAppointmentRequest request) {
    Page<AppointmentResponse> appointments = appointmentService.getAllAppointments(request);
    return new ResponseEntity<>(appointments, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable String id) {
    AppointmentResponse appointment = appointmentService.getAppointmentById(id);
    return new ResponseEntity<>(appointment, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<AppointmentResponse> createAppointment(
      @RequestBody CreateAppointmentRequest createAppointmentRequest) {
    AppointmentResponse createdAppointment =
        appointmentService.createAppointment(createAppointmentRequest);
    return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
  }

  @PutMapping("/{appointmentId}")
  public ResponseEntity<AppointmentResponse> updateAppointment(
      @PathVariable String appointmentId, @RequestBody UpdateAppointmentRequest request) {
    AppointmentResponse updatedAppointment =
        appointmentService.updateAppointment(appointmentId, request);
    return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
  }

  @DeleteMapping("/{appointmentId}")
  public ResponseEntity<SimpleResponse> deleteAppointment(@PathVariable String appointmentId) {
    Appointment appointment = appointmentService.deleteAppointment(appointmentId);
    SimpleResponse simpleResponse = AppointmentMapper.toAppointmentSimpleResponse(appointment);
    return new ResponseEntity<>(simpleResponse, HttpStatus.OK);
  }
}
