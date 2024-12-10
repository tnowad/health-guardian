package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.entities.Appointment;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class AppointmentSpecification implements Specification<Appointment> {

  private final ListAppointmentRequest request;

  @Override
  public Predicate toPredicate(
      Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    // Filter by patient ID
    if (request.getPatientId() != null && !request.getPatientId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("patient").get("id"), request.getPatientId()));
    }

    // Filter by doctor ID
    if (request.getDoctorId() != null && !request.getDoctorId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("doctor").get("id"), request.getDoctorId()));
    }

    // Filter by appointment date
    if (request.getAppointmentDate() != null) {
      predicates.add(
          criteriaBuilder.equal(root.get("appointmentDate"), request.getAppointmentDate()));
    }

    // Filter by reason for visit
    if (request.getReasonForVisit() != null && !request.getReasonForVisit().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("reasonForVisit")),
              "%" + request.getReasonForVisit().toLowerCase() + "%"));
    }

    // Filter by appointment status
    if (request.getStatus() != null) {
      predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
