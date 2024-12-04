package com.example.health_guardian_server.dtos.requests;

import org.springframework.data.jpa.domain.Specification;

import com.example.health_guardian_server.entities.UserStaff;
import com.example.health_guardian_server.specifications.UserStaffSpecification;

import lombok.Data;

@Data
public class ListUserStaffRequest implements PageableRequest<UserStaff>, PageableWithIdsRequest<String> {

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String search = "";

  private String type;

  private String[] ids;

  @Override
  public Specification<UserStaff> toSpecification() {
    return new UserStaffSpecification(this);
  }
}
