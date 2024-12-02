package com.example.health_guardian_server.dtos.requests;

import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.specifications.MedicationSpecification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListMedicationRequest implements PageableRequest<Medication> {
  private String name;
  private String activeIngredient;
  private String dosageForm;
  private String standardDosage;
  private String manufacturer;

  private Integer page = 0;
  private Integer size = 10;
  private String[] sortFields = new String[] { "name" };
  private Boolean[] desc = new Boolean[] { false };

  @Override
  public Specification<Medication> toSpecification() {
    return new MedicationSpecification(this);
  }
}
