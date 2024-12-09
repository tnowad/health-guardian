package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListAllergiesRequest;
import com.example.health_guardian_server.entities.Allergy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

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

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
