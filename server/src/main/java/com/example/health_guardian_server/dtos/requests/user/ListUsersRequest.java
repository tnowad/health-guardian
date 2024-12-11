package com.example.health_guardian_server.dtos.requests.user;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.enums.GenderType;
import com.example.health_guardian_server.specifications.UserSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListUsersRequest implements PageableRequest<User>, PageableWithIdsRequest<String> {

  private Integer page;

  private Integer size;

  private String[] sortFields;

  private Boolean[] desc;

  private String[] ids;

  private String email;

  private String firstName;

  private String lastName;

  private String address;

  private GenderType gender;

  @Override
  public Specification<User> toSpecification() {
    return new UserSpecification(this);
  }
}
