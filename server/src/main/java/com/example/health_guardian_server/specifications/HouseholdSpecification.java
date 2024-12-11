package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.household.ListHouseholdsRequest;
import com.example.health_guardian_server.entities.Household;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class HouseholdSpecification implements Specification<Household> {

  private final ListHouseholdsRequest request;

  @Override
  public Predicate toPredicate(
      Root<Household> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (request.getName() != null && !request.getName().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
    }

    if (request.getHeadId() != null && !request.getHeadId().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("head").get("id"), request.getHeadId()));
    }

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
