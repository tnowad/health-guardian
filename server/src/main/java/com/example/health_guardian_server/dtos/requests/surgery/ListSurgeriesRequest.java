package com.example.health_guardian_server.dtos.requests.surgery;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.Surgery;
import com.example.health_guardian_server.specifications.SurgerySpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListSurgeriesRequest
    implements PageableRequest<Surgery>, PageableWithIdsRequest<String> {
  String[] ids;
  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String userId;

  private Date startDate;

  private Date endDate;

  private String description;

  @Override
  public Specification<Surgery> toSpecification() {
    return new SurgerySpecification(this);
  }
}
