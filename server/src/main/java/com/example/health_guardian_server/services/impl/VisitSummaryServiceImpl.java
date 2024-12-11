package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.dtos.requests.visit_summary.CreateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.ListVisitSummaryRequest;
import com.example.health_guardian_server.dtos.requests.visit_summary.UpdateVisitSummaryRequest;
import com.example.health_guardian_server.dtos.responses.SimpleResponse;
import com.example.health_guardian_server.dtos.responses.visit_summary.VisitSummaryResponse;
import com.example.health_guardian_server.entities.Appointment;
import com.example.health_guardian_server.entities.User;
import com.example.health_guardian_server.entities.VisitSummary;
import com.example.health_guardian_server.mappers.VisitSummaryMapper;
import com.example.health_guardian_server.repositories.UserRepository;
import com.example.health_guardian_server.repositories.VisitSummaryRepository;
import com.example.health_guardian_server.services.VisitSummaryService;
import com.example.health_guardian_server.specifications.AppointmentSpecification;
import com.example.health_guardian_server.specifications.VisitSummarySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitSummaryServiceImpl implements VisitSummaryService {
  private final VisitSummaryRepository visitSummaryRepository;
  private final VisitSummaryMapper visitSummaryMapper;
  private final UserRepository userRepository;


  @Override
  public Page<VisitSummaryResponse> getAllVisitSummaries(ListVisitSummaryRequest request) {
    log.debug("Fetching all visit summaries with request: {}", request);
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
    VisitSummarySpecification specification = new VisitSummarySpecification((request);

    var visitSummaries =
      visitSummaryRepository
        .findAll(specification, pageRequest)
        .map(visitSummaryMapper::toVisitSummaryResponse);

    log.info("Fetched {} visit summaries", visitSummaries.getTotalElements());
    return visitSummaries;
  }

  @Override
  public VisitSummaryResponse getVisitSummaryById(String visitSummaryId) {
    log.debug("Fetching visit summary with id: {}", visitSummaryId);
    return visitSummaryRepository
      .findById(visitSummaryId)
      .map(visitSummaryMapper::toVisitSummaryResponse)
      .orElseThrow(
        () -> {
          log.error("Visit summary not found with id: {}", visitSummaryId);
          return new ResourceNotFoundException(
            "Visit summary not found with id " + visitSummaryId);
        });
  }

  @Override
  public VisitSummaryResponse createVisitSummary(CreateVisitSummaryRequest request) {
    log.debug("Creating visit summary: {}", request);
    VisitSummary createdVisitSummary = visitSummaryMapper.toVisitSummary(request);
    Optional<User> user = userRepository.findById(request.getUserId());
    createdVisitSummary.setUser(user.get());
    VisitSummary visitSummary = visitSummaryRepository.save(createdVisitSummary);
    log.info("Visit summary created with id: {}", createdVisitSummary.getId());
    return visitSummaryMapper.toVisitSummaryResponse(visitSummary);
  }

  @Override
  public VisitSummaryResponse updateVisitSummary(String visitSummaryId, UpdateVisitSummaryRequest request) {
    log.debug("Updating visit summary with id: {}", visitSummaryId);
    VisitSummary existingVisitSummary =
      visitSummaryRepository
        .findById(visitSummaryId)
        .orElseThrow(
          () -> {
            log.error("Visit summary not found with id: {}", visitSummaryId);
            return new ResourceNotFoundException(
              "Visit summary not found with id " + visitSummaryId);
          });

    VisitSummary updatedVisitSummary = visitSummaryMapper.toVisitSummary(request);
    updatedVisitSummary.setUser(existingVisitSummary.getUser());

    VisitSummary visitSummary = visitSummaryRepository.save(updatedVisitSummary);
    log.info("Visit summary updated with id: {}", request.getId());
    return visitSummaryMapper.toVisitSummaryResponse(visitSummary);
  }

  @Override
  public SimpleResponse deleteVisitSummary(String visitSummaryId) {
    log.debug("Deleting visit summary with id: {}", visitSummaryId);
    VisitSummary existingVisitSummary =
      visitSummaryRepository
        .findById(visitSummaryId)
        .orElseThrow(
          () -> {
            log.error("Visit summary not found with id: {}", visitSummaryId);
            return new ResourceNotFoundException(
              "Visit summary not found with id " + visitSummaryId);
          });

    visitSummaryRepository.delete(existingVisitSummary);
    log.info("Visit summary deleted with id: {}", visitSummaryId);
    return VisitSummaryMapper.toVisitSummarySimpleResponse(existingVisitSummary);
  }
}
