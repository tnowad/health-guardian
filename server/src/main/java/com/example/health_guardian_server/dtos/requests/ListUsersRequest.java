package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.specifications.UserSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListUsersRequest implements PageableRequest<User> {

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] {"id"};

  private Boolean[] desc = new Boolean[] {false};

  private String search = "";

  private String type;

  private String[] ids;

  @Override
  public Specification<User> toSpecification() {
    return new UserSpecification(this);
  }
}
