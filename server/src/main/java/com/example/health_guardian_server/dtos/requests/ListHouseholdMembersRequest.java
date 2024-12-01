package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.HouseholdMember;
import com.example.health_guardian_server.specifications.HouseholdMemberSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListHouseholdMembersRequest implements PageableRequest<HouseholdMember> {

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String householdId;

  private String patientId;

  private String relationshipToPatient;

  @Override
  public Specification<HouseholdMember> toSpecification() {
    return new HouseholdMemberSpecification(this);
  }
}
