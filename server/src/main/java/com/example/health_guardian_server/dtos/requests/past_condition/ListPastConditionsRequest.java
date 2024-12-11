package com.example.health_guardian_server.dtos.requests.past_condition;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.PastCondition;
import com.example.health_guardian_server.specifications.PastConditionSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListPastConditionsRequest
    implements PageableRequest<PastCondition>, PageableWithIdsRequest<String> {
  String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String userId;

  private String condition;

  private Date startDate;

  private Date endDate;

  @Override
  public Specification<PastCondition> toSpecification() {
    return new PastConditionSpecification(this);
  }
}
