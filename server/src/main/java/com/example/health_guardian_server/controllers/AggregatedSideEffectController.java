package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateAggregatedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListAggregatedSideEffectsRequest;
import com.example.health_guardian_server.dtos.responses.AggregatedSideEffectResponse;
import com.example.health_guardian_server.services.AggregatedSideEffectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aggregated-side-effects")
@RequiredArgsConstructor
public class AggregatedSideEffectController {

  private final AggregatedSideEffectService aggregatedSideEffectService;

  @GetMapping
  public ResponseEntity<Page<AggregatedSideEffectResponse>> listAggregatedSideEffects(
      @ModelAttribute ListAggregatedSideEffectsRequest request) {
    Page<AggregatedSideEffectResponse> response = aggregatedSideEffectService.listAggregatedSideEffects(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<AggregatedSideEffectResponse> createAggregatedSideEffect(
      @RequestBody CreateAggregatedSideEffectRequest request) {
    AggregatedSideEffectResponse response = aggregatedSideEffectService.createAggregatedSideEffect(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{aggregatedSideEffectId}")
  public ResponseEntity<AggregatedSideEffectResponse> getAggregatedSideEffect(
      @PathVariable String aggregatedSideEffectId) {
    AggregatedSideEffectResponse response = aggregatedSideEffectService.getAggregatedSideEffect(aggregatedSideEffectId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{aggregatedSideEffectId}")
  public ResponseEntity<AggregatedSideEffectResponse> updateAggregatedSideEffect(
      @PathVariable String aggregatedSideEffectId,
      @RequestBody UpdateAggregatedSideEffectRequest request) {
    AggregatedSideEffectResponse response = aggregatedSideEffectService
        .updateAggregatedSideEffect(aggregatedSideEffectId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{aggregatedSideEffectId}")
  public ResponseEntity<Void> deleteAggregatedSideEffect(
      @PathVariable String aggregatedSideEffectId) {
    aggregatedSideEffectService.deleteAggregatedSideEffect(aggregatedSideEffectId);
    return ResponseEntity.noContent().build();
  }
}
