package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.user.ListUsersRequest;
import com.example.health_guardian_server.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {
  private final ListUsersRequest request;

  @Override
  public Predicate toPredicate(
      Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (request.getEmail() != null && !request.getEmail().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
    }

    if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(root.get("firstName"), "%" + request.getFirstName() + "%"));
    }

    if (request.getLastName() != null && !request.getLastName().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + request.getLastName() + "%"));
    }

    if (request.getAddress() != null && !request.getAddress().isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("address"), "%" + request.getAddress() + "%"));
    }

    if (request.getGender() != null) {
      predicates.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
    }

    if (request.getIds() != null && request.getIds().length > 0) {
      predicates.add(root.get("id").in((Object[]) request.getIds()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
