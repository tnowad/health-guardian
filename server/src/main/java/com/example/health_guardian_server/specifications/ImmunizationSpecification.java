package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListImmunizationsRequest;
import com.example.health_guardian_server.entities.Immunization;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ImmunizationSpecification implements Specification<Immunization> {

  private final ListImmunizationsRequest request;

  @Override
  public Predicate toPredicate(
      Root<Immunization> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();
    if (request.getUserId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("userId"), request.getUserId()));
    }
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
