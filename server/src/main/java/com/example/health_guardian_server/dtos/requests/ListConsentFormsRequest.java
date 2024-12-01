package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.ConsentForm;
import com.example.health_guardian_server.specifications.ConsentFormSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListConsentFormsRequest implements PageableRequest<ConsentForm> {

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String patientId;

  private String formName;

  private String status;

  private Date consentDate;

  @Override
  public Specification<ConsentForm> toSpecification() {
    return new ConsentFormSpecification(this);
  }
}
