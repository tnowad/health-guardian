package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.past_condition.CreatePastConditionRequest;
import com.example.health_guardian_server.dtos.requests.past_condition.ListPastConditionsRequest;
import com.example.health_guardian_server.dtos.responses.past_condition.PastConditionResponse;
import com.example.health_guardian_server.services.PastConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/past-conditions")
@RequiredArgsConstructor
public class PastConditionController {
  private final PastConditionService pastConditionService;

  @GetMapping
  public ResponseEntity<Page<PastConditionResponse>> listPastConditions(@ModelAttribute ListPastConditionsRequest request) {
    Page<PastConditionResponse> pastConditions = pastConditionService.getAllPastConditions(request);
    return ResponseEntity.ok(pastConditions);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PastConditionResponse> getPastCondition(@PathVariable String id) {
    PastConditionResponse pastCondition = pastConditionService.getPastConditionById(id);
    return ResponseEntity.ok(pastCondition);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePastCondition(@PathVariable String id) {
    pastConditionService.deletePastCondition(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<PastConditionResponse> updatePastCondition(@PathVariable String id,@RequestBody CreatePastConditionRequest request) {
    PastConditionResponse pastCondition = pastConditionService.updatePastCondition(id, request);
    return ResponseEntity.ok(pastCondition);
  }
  @PostMapping
  public ResponseEntity<PastConditionResponse> createPastCondition(@RequestBody CreatePastConditionRequest request) {
    PastConditionResponse pastCondition = pastConditionService.createPastCondition(request);
    return ResponseEntity.ok(pastCondition);
  }
}
