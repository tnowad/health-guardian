package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.responses.AppointmentResponse;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
  @PostMapping
  public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody Appointment appointment) {
    AppointmentResponse createdAppointment = appointmentService.createAppointment(appointment);
    return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable String id) {
    AppointmentResponse appointment = appointmentService.getAppointmentById(id);
    return new ResponseEntity<>(appointment, HttpStatus.OK);
  }
  @GetMapping
  public ResponseEntity<Page<AppointmentResponse>> getAllAppointments(@ModelAttribute ListAppointmentRequest request) {
    Page<AppointmentResponse> appointments = appointmentService.getAllAppointments(request);
    return new ResponseEntity<>(appointments, HttpStatus.OK);
  }

  @PutMapping("/{appointmentId}")
  public ResponseEntity<AppointmentResponse> updateAppointment( @PathVariable String id,@RequestBody Appointment appointment) {
    AppointmentResponse updatedAppointment = appointmentService.updateAppointment(id, appointment);
    return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
  }

  @DeleteMapping("/{appointmentId}")
  public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
    appointmentService.deleteAppointment(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


//   @PostMapping("/send")
//
//  public ResponseEntity<String> sendNotification() {
//     for (Appointment appointment : appointmentService.get()) {
//       User user = userService.getUserById(appointment.getPatient().getId());
//       Account account = accountService.getAccountByUserId(user.getId());
//       if (account.getExternalProviders() != null) {
//         for (ExternalProvider provider : account.getExternalProviders()) {
//           notificationService.sendEmail(provider.getProviderUserEmail(), "Notification", "Your next appointment is coming up soon!: " + appointment.getAppointmentDate());
//         }
//       }
//       if (account.getLocalProviders() != null) {
//         for (ExternalProvider provider : account.getExternalProviders()) {
//           notificationService.sendEmail(provider.getProviderUserEmail(), "Notification", "Your next appointment is coming up soon!: " + appointment.getAppointmentDate());
//         }
//       }
//     }
//
//      return ResponseEntity.ok("Emails sent to all Users that have appointments");
//  }

}
