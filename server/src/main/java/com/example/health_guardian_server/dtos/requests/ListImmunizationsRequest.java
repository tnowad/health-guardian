package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.Immunization;
import com.example.health_guardian_server.specifications.ImmunizationSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListImmunizationsRequest implements PageableRequest<Immunization>, PageableWithIdsRequest<String> {
  String[] ids;
  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String userId;

  @Override
  public Specification<Immunization> toSpecification() {
    return new ImmunizationSpecification(this);
  }
}
