package com.example.health_guardian_server.dtos.requests.appointment;

import com.example.health_guardian_server.dtos.requests.PageableRequest;
import com.example.health_guardian_server.dtos.requests.PageableWithIdsRequest;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.enums.AppointmentStatus;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListAppointmentRequest
    implements PageableRequest<Appointment>, PageableWithIdsRequest<String> {
  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "id" };

  private Boolean[] desc = new Boolean[] { false };

  private String[] ids;

  private String userId;

  private Date startDate;

  private Date endDate;

  private String reason;

  private String address;

  private AppointmentStatus status;

  private String notes;

  @Override
  public Specification<Appointment> toSpecification() {
    return new AppointmentSpecification(this);
  }

  @Override
  public Pageable toPageable() {
    if (sortFields != null && sortFields.length > 0) {
      String sortBy = sortFields[0];
      boolean isDescending = desc != null && desc.length > 0 && desc[0];
      return PageRequest.of(
          page, size, isDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
    }
    return PageRequest.of(page, size); // Default page and size if no sorting
  }
}
