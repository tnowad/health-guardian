package com.example.health_guardian_server.dtos.requests.physician_note;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.PhysicianNote;
import com.example.health_guardian_server.specifications.PhysicianNoteSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListPhysicianNotesRequest
    implements PageableRequest<PhysicianNote>, PageableWithIdsRequest<String> {

  String[] ids;
  private Integer page = 0;
  private Integer size = 10;
  private String[] sortFields = new String[] { "id" };
  private Boolean[] desc = new Boolean[] { false };

  private String userId;
  private Date startDate;
  private Date endDate;
  private String note;

  @Override
  public Specification<PhysicianNote> toSpecification() {
    return new PhysicianNoteSpecification(this);
  }
}
