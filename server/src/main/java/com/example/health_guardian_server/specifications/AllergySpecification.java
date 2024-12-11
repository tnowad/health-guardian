package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.allergy.ListAllergiesRequest;
import com.example.health_guardian_server.entities.Allergy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class AllergySpecification implements Specification<Allergy> {

  private final ListAllergiesRequest request;

  @Override
  public Predicate toPredicate(
      Root<Allergy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by user ID
    if (request.getUserId() != null && !request.getUserId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
    }

    // Filter by allergy name (partial match)
    if (request.getAllergyName() != null && !request.getAllergyName().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("allergyName"), "%" + request.getAllergyName() + "%"));
    }

    // Filter by severity
    if (request.getSeverity() != null && !request.getSeverity().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("severity"), request.getSeverity()));
    }

    // Filter by specific IDs
    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
