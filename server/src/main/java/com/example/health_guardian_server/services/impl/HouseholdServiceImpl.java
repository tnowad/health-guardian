package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdResponse;
import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.mappers.HouseholdMapper;
import com.example.health_guardian_server.repositories.HouseholdRepository;
import com.example.health_guardian_server.services.HouseholdService;
import com.example.health_guardian_server.specifications.HouseholdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

  private final HouseholdRepository householdRepository;
  private final HouseholdMapper householdMapper;

  @Override
  public Page<HouseholdResponse> listHouseholds(ListHouseholdsRequest request) {

    log.debug("Fetching all appointments with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    HouseholdSpecification specification = new HouseholdSpecification(request);

    var households = householdRepository
        .findAll(specification, pageRequest)
        .map(householdMapper::toHouseholdResponse);

    log.info("Fetched {} appointments", households.getTotalElements());
    return households;
  }

  @Override
  public HouseholdResponse createHousehold(CreateHouseholdRequest request) {

    log.debug("Creating household: {}", request);
    Household createdHousehold = householdRepository.save(householdMapper.toHousehold(request));
    return householdMapper.toHouseholdResponse(createdHousehold);
  }

  @Override
  public HouseholdResponse getHousehold(String householdId) {

    log.debug("Fetching household with id: {}", householdId);
    return householdRepository
        .findById(householdId)
        .map(householdMapper::toHouseholdResponse)
        .orElseThrow(
            () -> {
              log.error("Household not found with id: {}", householdId);
              return new ResourceNotFoundException("Household not found with id " + householdId);
            });
  }

  @Override
  public HouseholdResponse updateHousehold(String householdId, CreateHouseholdRequest request) {

    log.debug("Updating household with id: {}", householdId);
    Household household = householdRepository
        .findById(householdId)
        .orElseThrow(
            () -> {
              log.error("Household not found with id: {}", householdId);
              return new ResourceNotFoundException(
                  "Household not found with id " + householdId);
            });

    // household.setHead(userRe.getReferenceById(request.getHeadId()));

    Household updatedHousehold = householdRepository.save(household);
    log.info("Household updated with id: {}", updatedHousehold.getId());
    return householdMapper.toHouseholdResponse(updatedHousehold);
  }

  @Override
  public void deleteHousehold(String householdId) {

    log.debug("Deleting household with id: {}", householdId);
    Household household = householdRepository
        .findById(householdId)
        .orElseThrow(
            () -> {
              log.error("Household not found with id: {}", householdId);
              return new ResourceNotFoundException(
                  "Household not found with id " + householdId);
            });

    householdRepository.delete(household);
    log.info("Household deleted with id: {}", householdId);
  }
}
