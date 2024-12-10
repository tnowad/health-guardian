package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListHouseholdsRequest;
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

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
