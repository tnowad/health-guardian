package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.household.CreateHouseholdRequest;
import com.example.health_guardian_server.dtos.requests.household.ListHouseholdsRequest;
import com.example.health_guardian_server.dtos.responses.household.HouseholdResponse;
import com.example.health_guardian_server.entities.Household;
import com.example.health_guardian_server.entities.HouseholdMember;
import com.example.health_guardian_server.mappers.HouseholdMapper;
import com.example.health_guardian_server.repositories.HouseholdMemberRepository;
import com.example.health_guardian_server.repositories.HouseholdRepository;
import com.example.health_guardian_server.services.HouseholdService;
import com.example.health_guardian_server.services.MinioClientService;
import com.example.health_guardian_server.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

  private final HouseholdRepository householdRepository;
  private final HouseholdMapper householdMapper;
  private final HouseholdMemberRepository householdMemberRepository;
  private final UserService userService;
  private final MinioClientService minioClientService;

  @Override
  public Page<HouseholdResponse> listHouseholds(ListHouseholdsRequest request) {

    log.debug("Fetching all appointments with request: {}", request);

    var households = householdRepository
        .findAll(request.toSpecification(), request.toPageable())
        .map(householdMapper::toHouseholdResponse);
    households.forEach(
        household -> {
          String defaultAvatarUrl = "https://default-avatar-url.com";
          try {
            String avatarUrl = minioClientService.getObjectUrl(household.getAvatar(), "files");
            log.debug("Avatar URL retrieved for household {}: {}", household.getId(), avatarUrl);
            household.setAvatar(avatarUrl);
          } catch (Exception e) {
            log.error("Error when retrieving avatar URL for household: {}", household.getId(), e);
            household.setAvatar(defaultAvatarUrl); // Set URL mặc định nếu có lỗi
          }
        });
    log.info("Fetched {} appointments", households.getTotalElements());
    return households;
  }

  @Override
  @Transactional
  public HouseholdResponse createHousehold(CreateHouseholdRequest request) {
    log.debug("Creating household with request: {}", request);

    log.debug("Mapping request to Household entity...");
    Household createdHousehold = householdMapper.toHousehold(request);

    log.debug("Setting household members with head user ID: {}", request.getHeadId());
    createdHousehold.setHouseholdMembers(
        List.of(
            HouseholdMember.builder()
                .household(createdHousehold)
                .user(userService.getUserById(request.getHeadId()))
                .build()));

    log.debug("Saving household to the repository...");
    createdHousehold = householdRepository.save(createdHousehold);

    log.debug("Mapping created Household entity to response...");
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
    var householdMember = householdMemberRepository.findAllByHousehold(household);
    householdMemberRepository.deleteAllInBatch(householdMember);
    householdRepository.delete(household);
    log.info("Household deleted with id: {}", householdId);
  }

  @Override
  public Household getHouseholdEntity(String householdId) {
    log.debug("Fetching household with id: {}", householdId);
    return householdRepository
        .findById(householdId)
        .orElseThrow(
            () -> {
              log.error("Household not found with id: {}", householdId);
              return new ResourceNotFoundException("Household not found with id " + householdId);
            });
  }
}
