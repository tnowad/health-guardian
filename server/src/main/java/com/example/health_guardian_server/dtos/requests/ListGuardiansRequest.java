package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.Guardian;
import com.example.health_guardian_server.specifications.GuardianSpecification;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListGuardiansRequest implements PageableRequest<Guardian>, PageableWithIdsRequest<String> {
  String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String search = "";

  private String relationshipToPatient;

  private String phone;

  private String email;

  @Override
  public Specification<Guardian> toSpecification() {
    return new GuardianSpecification(this);
  }

  @Override
  public Pageable toPageable() {
    if (sortFields != null && sortFields.length > 0) {
      String sortBy = sortFields[0];
      boolean isDescending = desc != null && desc.length > 0 && desc[0];
      return PageRequest.of(
          page, size, isDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
    }
    return PageRequest.of(page, size); // Default page and size if no sorting
  }
}
