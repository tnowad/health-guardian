package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.AppointmentStatus;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import java.util.Date;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListAppointmentRequest implements PageableRequest<Appointment>, PageableWithIdsRequest<String> {
  String[] ids;

  private Integer page = 0;

  private Integer size = 10;

  private String[] sortFields = new String[] { "appointmentDate" };

  private Boolean[] desc = new Boolean[] { false };

  private String patientId;

  private String doctorId;

  private Date appointmentDate;

  private String reasonForVisit;

  private AppointmentStatus status;

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
