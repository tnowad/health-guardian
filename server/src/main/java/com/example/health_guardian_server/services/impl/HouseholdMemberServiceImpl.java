package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdMembersRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdMemberResponse;
import com.example.health_guardian_server.entities.HouseholdMember;
import com.example.health_guardian_server.mappers.HouseholdMemberMapper;
import com.example.health_guardian_server.repositories.HouseholdMemberRepository;
import com.example.health_guardian_server.services.HouseholdMemberService;
import com.example.health_guardian_server.services.HouseholdService;
import com.example.health_guardian_server.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class HouseholdMemberServiceImpl implements HouseholdMemberService {
  private final HouseholdMemberRepository householdMemberRepository;
  private final HouseholdMemberMapper householdMemberMapper;
  private final UserService userService;
  private final HouseholdService householdService;

  @Override
  public Page<HouseholdMemberResponse> listHouseholdMembers(ListHouseholdMembersRequest request) {
    log.debug("Fetching all household members with request: {}", request);
    var householdMembers = householdMemberRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(householdMemberMapper::toHouseholdMemberResponse);

    log.info("Fetched {} household members", householdMembers.getTotalElements());
    return householdMembers;
  }

  @Override
  public HouseholdMemberResponse createHouseholdMember(CreateHouseholdMemberRequest request) {
    var user = userService.getUserByEmail(request.getEmail());
    var household = householdService.getHouseholdEntity(request.getHouseholdId());
    if (user == null) {
      log.error("User not found with email: {}", request.getEmail());
      throw new ResourceNotFoundException("User not found with email " + request.getEmail());
    }
    if (household == null) {
      log.error("Household not found with id: {}", request.getHouseholdId());
      throw new ResourceNotFoundException(
          "Household not found with id " + request.getHouseholdId());
    }
    log.debug("Creating household member: {}", request);
    HouseholdMember createdHouseholdMember = householdMemberRepository.save(
        HouseholdMember.builder().user(user).household(household).build());

    log.info("Created household member with id: {}", createdHouseholdMember.getId());
    return householdMemberMapper.toHouseholdMemberResponse(createdHouseholdMember);
  }

  @Override
  public HouseholdMemberResponse createHouseholdMember(HouseholdMember request) {
    return householdMemberMapper.toHouseholdMemberResponse(householdMemberRepository.save(request));
  }

  @Override
  public HouseholdMemberResponse getHouseholdMember(String householdMemberId) {

    log.debug("Fetching household member with id: {}", householdMemberId);
    return householdMemberRepository
        .findById(householdMemberId)
        .map(householdMemberMapper::toHouseholdMemberResponse)
        .orElseThrow(
            () -> {
              log.error("Household member not found with id: {}", householdMemberId);
              return new ResourceNotFoundException(
                  "Household member not found with id " + householdMemberId);
            });
  }

  @Override
  public HouseholdMemberResponse updateHouseholdMember(
      String householdMemberId, CreateHouseholdMemberRequest request) {

    log.debug("Updating household member with id: {}", householdMemberId);
    HouseholdMember existingHouseholdMember = householdMemberRepository
        .findById(householdMemberId)
        .orElseThrow(
            () -> {
              log.error("Household member not found with id: {}", householdMemberId);
              return new ResourceNotFoundException(
                  "Household member not found with id " + householdMemberId);
            });
    HouseholdMember updatedHouseholdMember = householdMemberRepository.save(existingHouseholdMember);
    log.info("Household member updated with id: {}", updatedHouseholdMember.getId());
    return householdMemberMapper.toHouseholdMemberResponse(updatedHouseholdMember);
  }

  @Override
  public void deleteHouseholdMember(String householdMemberId) {

  }

  @Override
  public void deleteHouseholdMember(String householdMemberId, String householdId) {
    log.debug("Deleting household member with id: {}", householdMemberId);
    var household = householdService.getHouseholdEntity(householdId);
    HouseholdMember existingHouseholdMember = householdMemberRepository
        .findByIdAndHousehold(householdMemberId, household)
        .orElseThrow(
            () -> {
              log.error("Household member not found with id: {}", householdMemberId);
              return new ResourceNotFoundException(
                  "Household member not found with id " + householdMemberId);
            });
    householdMemberRepository.delete(existingHouseholdMember);
    log.info("Deleted household member with id: {}", householdMemberId);
  }
}
