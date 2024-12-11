package com.example.health_guardian_server.dtos.requests.diagnostic;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.DiagnosticReport;
import com.example.health_guardian_server.entities.enums.ReportType;
import com.example.health_guardian_server.specifications.DiagnosticReportSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListDiagnosticReportRequest
    implements PageableRequest<DiagnosticReport>, PageableWithIdsRequest<String> {

  String[] ids;
  private Integer page = 0;
  private Integer size = 10;
  private String[] sortFields = new String[] { "id" };
  private Boolean[] desc = new Boolean[] { false };

  private String userId;
  private Date startDate;
  private Date endDate;
  private ReportType reportType;
  private String summary;
  private String notes;

  @Override
  public Specification<DiagnosticReport> toSpecification() {
    return new DiagnosticReportSpecification(this);
  }
}
