package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.family_history.ListFamilyHistoryRequest;
import com.example.health_guardian_server.dtos.responses.family_history.FamilyHistoryResponse;
import com.example.health_guardian_server.mappers.FamilyHistoryMapper;
import com.example.health_guardian_server.repositories.FamilyHistoryRepository;
import com.example.health_guardian_server.services.FamilyHistoryService;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import com.example.health_guardian_server.specifications.FamilyHistorySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyHistoryServiceImpl implements FamilyHistoryService {

  private final FamilyHistoryRepository familyHistoryRepository;
  private final FamilyHistoryMapper familyHistoryMapper;

  @Override
  public Page<FamilyHistoryResponse> getAllFamilyHistories(ListFamilyHistoryRequest request) {
    log.debug("Fetching all family histories with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    FamilyHistorySpecification specification = new FamilyHistorySpecification(request);

    var familyHistories =
      familyHistoryRepository
        .findAll(specification, pageRequest)
        .map(familyHistoryMapper::toResponse);

    log.info("Fetched {} family histories", familyHistories.getTotalElements());
    return familyHistories;
  }
}
