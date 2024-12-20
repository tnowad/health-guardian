package com.example.health_guardian_server.dtos.requests.immunization;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.Immunization;
import com.example.health_guardian_server.specifications.ImmunizationSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListImmunizationsRequest
    implements PageableRequest<Immunization>, PageableWithIdsRequest<String> {
  String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String userId;

  private String vaccineName;

  private Date startDate;

  private Date endDate;

  @Override
  public Specification<Immunization> toSpecification() {
    return new ImmunizationSpecification(this);
  }
}
