package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.entities.Account;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.ExternalProvider;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.services.AccountService;
import com.example.health_guardian_server.services.AppointmentService;
import com.example.health_guardian_server.services.NotificationService;
import com.example.health_guardian_server.services.UserService;
import java.util.List;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

  private final AppointmentService appointmentService;
  private final NotificationService notificationService;
  private final UserService userService;
  private final AccountService accountService;

  public AppointmentController(
      AppointmentService appointmentService,
      NotificationService notificationService,
      UserService userService,
      AccountService accountService) {
    this.appointmentService = appointmentService;
    this.notificationService = notificationService;
    this.userService = userService;
    this.accountService = accountService;
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

  @PostMapping("/send")
  public ResponseEntity<String> sendNotification() {
    for (Appointment appointment : appointmentService.getAppointments()) {
      User user = userService.getUserById(appointment.getPatient().getId());
      Account account = accountService.getAccountByUserId(user.getId());
      if (account.getExternalProviders() != null) {
        for (ExternalProvider provider : account.getExternalProviders()) {
          notificationService.sendEmail(
              provider.getProviderUserEmail(),
              "Notification",
              "Your next appointment is coming up soon!: " + appointment.getAppointmentDate());
        }
      }
      if (account.getLocalProviders() != null) {
        for (ExternalProvider provider : account.getExternalProviders()) {
          notificationService.sendEmail(
              provider.getProviderUserEmail(),
              "Notification",
              "Your next appointment is coming up soon!: " + appointment.getAppointmentDate());
        }
      }
    }

    return ResponseEntity.ok("Emails sent to all Users that have appointments");
  }
}
