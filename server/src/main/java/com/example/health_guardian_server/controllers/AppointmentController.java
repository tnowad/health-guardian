package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")

public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @PostMapping("/create")
  public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
    Appointment createdAppointment = appointmentService.createAppointment(appointment);
    return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
    Appointment appointment = appointmentService.getAppointmentById(id);
    if (appointment == null) {
      throw new ResourceNotFoundException("Appointment not found with id " + id);
    }
    return new ResponseEntity<>(appointment, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Appointment>> getAllAppointments() {
    return new ResponseEntity<>(appointmentService.getAppointments(), HttpStatus.OK);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) {
    Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
    return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
    appointmentService.deleteAppointment(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
