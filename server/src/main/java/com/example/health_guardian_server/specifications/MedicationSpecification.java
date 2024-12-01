package com.example.health_guardian_server.specifications;

import com.example.health_guardian_server.dtos.requests.ListMedicationRequest;
import com.example.health_guardian_server.entities.Medication;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class MedicationSpecification implements Specification<Medication> {

  private final ListMedicationRequest request;

  @Override
  public Predicate toPredicate(
      Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    // Filter by medication name
    if (request.getName() != null && !request.getName().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("name")),
              "%" + request.getName().toLowerCase() + "%"));
    }

    // Filter by active ingredient
    if (request.getActiveIngredient() != null && !request.getActiveIngredient().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("activeIngredient")),
              "%" + request.getActiveIngredient().toLowerCase() + "%"));
    }

    // Filter by dosage form
    if (request.getDosageForm() != null && !request.getDosageForm().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("dosageForm"), request.getDosageForm()));
    }

    // Filter by standard dosage
    if (request.getStandardDosage() != null && !request.getStandardDosage().isEmpty()) {
      predicates.add(
          criteriaBuilder.equal(root.get("standardDosage"), request.getStandardDosage()));
    }

    // Filter by manufacturer
    if (request.getManufacturer() != null && !request.getManufacturer().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("manufacturer"), request.getManufacturer()));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
