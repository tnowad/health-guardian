package com.example.health_guardian_server.dtos.requests.household;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.specifications.HouseholdSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListHouseholdsRequest
    implements PageableRequest<Household>, PageableWithIdsRequest<String> {
  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String[] ids;

  private String name;

  private String headId;

  @Override
  public Specification<Household> toSpecification() {
    return new HouseholdSpecification(this);
  }
}
