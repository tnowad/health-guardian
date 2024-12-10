package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.specifications.HouseholdSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListHouseholdsRequest
    implements PageableRequest<Household>, PageableWithIdsRequest<String> {
  String[] ids;
  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] {"id"};

  private Boolean[] desc = new Boolean[] {false};

  private String headPatientId;

  @Override
  public Specification<Household> toSpecification() {
    return new HouseholdSpecification(this);
  }
}
