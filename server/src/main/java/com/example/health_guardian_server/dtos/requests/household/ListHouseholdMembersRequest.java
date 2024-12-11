package com.example.health_guardian_server.dtos.requests.household;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.HouseholdMember;
import com.example.health_guardian_server.specifications.HouseholdMemberSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListHouseholdMembersRequest
    implements PageableRequest<HouseholdMember>, PageableWithIdsRequest<String> {
  private Integer page;

  private Integer size;

  private String[] sortFields;

  private Boolean[] desc;

  private String[] ids;

  private String householdId;

  private String userId;

  @Override
  public Specification<HouseholdMember> toSpecification() {
    return new HouseholdMemberSpecification(this);
  }
}
