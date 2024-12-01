package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListPatientRequest;
import com.example.health_guardian_server.entities.Patient;
import com.example.health_guardian_server.entities.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PatientSpecification implements Specification<Patient> {

  String search;
  String type;
  String[] ids;

  public PatientSpecification(ListPatientRequest request) {
    this.search = request.getSearch();
    this.type = request.getType();
    this.ids = request.getIds();
  }

  @Override
  public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (search != null && !search.isEmpty()) {
      predicates.add(criteriaBuilder.like(root.get("id"), "%" + search + "%"));
    }

    if (type != null && !type.isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("type"), type));
    }

    if (ids != null && ids.length > 0) {
      Join<Patient, User> join = root.join("users");
      predicates.add(join.get("id").in((Object[]) ids));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
