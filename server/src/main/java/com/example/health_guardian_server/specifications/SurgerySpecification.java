package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.surgery.ListSurgeriesRequest;
import com.example.health_guardian_server.entities.Surgery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class SurgerySpecification implements Specification<Surgery> {
  private final ListSurgeriesRequest request;

  @Override
  public Predicate toPredicate(
      Root<Surgery> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by surgery description (partial match)
    if (request.getDescription() != null && !request.getDescription().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("description"), "%" + request.getDescription() + "%"));
    }

    // Filter by surgery date range
    if (request.getStartDate() != null) {
      predicates.add(
          criteriaBuilder.greaterThanOrEqualTo(root.get("date"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), request.getEndDate()));
    }

    // Filter by specific IDs
    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
