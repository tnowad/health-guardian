package com.example.health_guardian_server.dtos.requests.diagnostic_result;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.DiagnosticResult;
import com.example.health_guardian_server.specifications.DiagnosticResultSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListDiagnosticResultRequest
    implements PageableRequest<DiagnosticResult>, PageableWithIdsRequest<String> {

  String[] ids;
  private Integer page = 0;
  private Integer size = 10;
  private String[] sortFields = new String[] { "id" };
  private Boolean[] desc = new Boolean[] { false };

  private String userId;
  private String testName;
  private Date startDate;
  private Date endDate;
  private String resultValue;
  private String notes;

  @Override
  public Specification<DiagnosticResult> toSpecification() {
    return new DiagnosticResultSpecification(this);
  }
}
