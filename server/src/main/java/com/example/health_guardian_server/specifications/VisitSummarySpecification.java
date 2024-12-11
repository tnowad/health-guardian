package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.appointment.ListAppointmentRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.VisitSummary;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class VisitSummarySpecification implements Specification<VisitSummary> {
  private final ListVisitSummaryRequest request;

  @Override
  public Predicate toPredicate(
    Root<VisitSummary> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by visit summary date
    if (request.getVisitDate() != null) {
      predicates.add(
        criteriaBuilder.equal(root.get("visitDate"), request.getVisitDate()));
    }

    // Filter by visit summary type
    if (request.getVisitType() != null) {
      predicates.add(criteriaBuilder.equal(root.get("visitType"), request.getVisitType()));
    }

    // Filter by visit summary notes
    if (request.getNotes() != null && !request.getNotes().isEmpty()) {
      predicates.add(
        criteriaBuilder.like(
          criteriaBuilder.lower(root.get("notes")),
          "%" + request.getNotes().toLowerCase() + "%"));
    }



    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
