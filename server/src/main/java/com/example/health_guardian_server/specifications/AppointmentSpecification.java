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

    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(
              root.get("appointmentDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(
          criteriaBuilder.lessThanOrEqualTo(root.get("appointmentDate"), request.getEndDate()));
    }

    if (request.getReason() != null && !request.getReason().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("reason"), "%" + request.getReason() + "%"));
    }

    if (request.getAddress() != null && !request.getAddress().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("address"), "%" + request.getAddress() + "%"));
    }

    if (request.getStatus() != null) {
      predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
    }

    if (request.getNotes() != null && !request.getNotes().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("notes"), "%" + request.getNotes() + "%"));
    }

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
