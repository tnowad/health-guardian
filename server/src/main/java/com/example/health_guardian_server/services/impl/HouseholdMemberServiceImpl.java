package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.ListHouseholdMembersRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdMemberResponse;
import com.example.health_guardian_server.entities.HouseholdMember;
import com.example.health_guardian_server.mappers.HouseholdMemberMapper;
import com.example.health_guardian_server.repositories.HouseholdMemberRepository;
import com.example.health_guardian_server.repositories.HouseholdRepository;
import com.example.health_guardian_server.services.HouseholdMemberService;
import com.example.health_guardian_server.specifications.HouseholdMemberSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class HouseholdMemberServiceImpl implements HouseholdMemberService {
  private final HouseholdMemberRepository householdMemberRepository;
  private final HouseholdMemberMapper householdMemberMapper;
  private final HouseholdRepository householdRepository;

  @Override
  public Page<HouseholdMemberResponse> listHouseholdMembers(ListHouseholdMembersRequest request) {
    log.debug("Fetching all household members with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    HouseholdMemberSpecification specification = new HouseholdMemberSpecification(request);

    var householdMembers =
        householdMemberRepository
            .findAll(specification, pageRequest)
            .map(householdMemberMapper::toHouseholdMemberResponse);

    log.info("Fetched {} household members", householdMembers.getTotalElements());
    return householdMembers;
  }

  @Override
  public HouseholdMemberResponse createHouseholdMember(CreateHouseholdMemberRequest request) {
    log.debug("Creating household member: {}", request);
    var householdMember = householdMemberMapper.toHouseholdMember(request);
    HouseholdMember createdHouseholdMember = householdMemberRepository.save(householdMember);

    log.info("Created household member with id: {}", createdHouseholdMember.getId());
    return householdMemberMapper.toHouseholdMemberResponse(createdHouseholdMember);
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
    HouseholdMember existingHouseholdMember =
        householdMemberRepository
            .findById(householdMemberId)
            .orElseThrow(
                () -> {
                  log.error("Household member not found with id: {}", householdMemberId);
                  return new ResourceNotFoundException(
                      "Household member not found with id " + householdMemberId);
                });
    HouseholdMember updatedHouseholdMember =
        householdMemberRepository.save(existingHouseholdMember);
    log.info("Household member updated with id: {}", updatedHouseholdMember.getId());
    return householdMemberMapper.toHouseholdMemberResponse(updatedHouseholdMember);
  }

  @Override
  public void deleteHouseholdMember(String householdMemberId) {
    log.debug("Deleting household member with id: {}", householdMemberId);
    HouseholdMember existingHouseholdMember =
        householdMemberRepository
            .findById(householdMemberId)
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
