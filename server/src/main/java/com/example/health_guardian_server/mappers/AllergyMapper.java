package com.example.health_guardian_server.mappers;

import com.example.health_guardian_server.dtos.requests.allergy.CreateAllergyRequest;
import com.example.health_guardian_server.dtos.responses.allergy.AllergyResponse;
import com.example.health_guardian_server.entities.Allergy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllergyMapper {
  // Define methods

  @Mapping(source = "user.id", target = "userId")
  AllergyResponse toResponse(Allergy allergy);

  Allergy toAllergy(CreateAllergyRequest allergy);
}
