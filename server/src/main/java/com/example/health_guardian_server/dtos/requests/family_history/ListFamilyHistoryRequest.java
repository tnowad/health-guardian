package com.example.health_guardian_server.dtos.requests.family_history;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.FamilyHistory;
import com.example.health_guardian_server.specifications.FamilyHistorySpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListFamilyHistoryRequest
    implements PageableRequest<FamilyHistory>, PageableWithIdsRequest<String> {

  String[] ids;
  private Integer page = 0;
  private Integer size = 10;
  private String[] sortFields = new String[] { "id" };
  private Boolean[] desc = new Boolean[] { false };

  private String userId;
  private String relation;
  private String condition;
  private String description;

  @Override
  public Specification<FamilyHistory> toSpecification() {
    return new FamilyHistorySpecification(this);
  }
}
