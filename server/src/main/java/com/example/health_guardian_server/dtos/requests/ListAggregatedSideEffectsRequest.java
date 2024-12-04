package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.AggregatedSideEffect;
import com.example.health_guardian_server.specifications.AggregatedSideEffectSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListAggregatedSideEffectsRequest
    implements PageableRequest<AggregatedSideEffect>, PageableWithIdsRequest<String> {
  String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String sideEffectId;

  private String medicationId;

  private Date periodStart;

  private Date periodEnd;

  @Override
  public Specification<AggregatedSideEffect> toSpecification() {
    return new AggregatedSideEffectSpecification(this);
  }
}
