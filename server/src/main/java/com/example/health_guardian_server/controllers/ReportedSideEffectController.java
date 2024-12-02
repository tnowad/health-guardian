package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateReportedSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.requests.UpdateReportedSideEffectsRequest;
import com.example.health_guardian_server.dtos.responses.ReportedSideEffectResponse;
import com.example.health_guardian_server.services.ReportedSideEffectService;
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
@RequestMapping("/report-side-effects")
@RequiredArgsConstructor
public class ReportedSideEffectController {

  private final ReportedSideEffectService reportedSideEffectService;

  @GetMapping
  public ResponseEntity<Page<ReportedSideEffectResponse>> listReportedSideEffects(
      @ModelAttribute ListReportedSideEffectsRequest request) {
    Page<ReportedSideEffectResponse> response =
        reportedSideEffectService.listReportedSideEffects(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ReportedSideEffectResponse> createReportSideEffect(
      @RequestBody CreateReportedSideEffectRequest request) {
    ReportedSideEffectResponse response =
        reportedSideEffectService.createReportedSideEffect(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{reportedSideEffectId}")
  public ResponseEntity<ReportedSideEffectResponse> getReportSideEffect(
      @PathVariable String reportSideEffectId) {
    ReportedSideEffectResponse response =
        reportedSideEffectService.getReportedSideEffect(reportSideEffectId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{reportSideEffectId}")
  public ResponseEntity<ReportedSideEffectResponse> updateReportedSideEffect(
      @PathVariable String reportedSideEffectId,
      @RequestBody UpdateReportedSideEffectsRequest request) {
    ReportedSideEffectResponse response =
        reportedSideEffectService.updateReportedSideEffect(reportedSideEffectId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{reportSideEffectId}")
  public ResponseEntity<Void> deleteReportedSideEffect(@PathVariable String reportedSideEffectId) {
    reportedSideEffectService.deleteReportedSideEffect(reportedSideEffectId);
    return ResponseEntity.noContent().build();
  }
}
