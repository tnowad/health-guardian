package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.ListSideEffectRequest;
import com.example.health_guardian_server.dtos.requests.UpdateSideEffectRequest;
import com.example.health_guardian_server.dtos.responses.SideEffectResponse;
import com.example.health_guardian_server.services.SideEffectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/side-effects")
@RequiredArgsConstructor
public class SideEffectController {

  private final SideEffectService sideEffectService;

  @GetMapping
  public ResponseEntity<Page<SideEffectResponse>> listSideEffects(ListSideEffectRequest request) {
    Page<SideEffectResponse> sideEffects = sideEffectService.listSideEffects(request);
    return ResponseEntity.ok(sideEffects);
  }

  @PostMapping
  public ResponseEntity<SideEffectResponse> createSideEffect(
      @RequestBody CreateSideEffectRequest request) {
    SideEffectResponse sideEffectResponse = sideEffectService.createSideEffect(request);
    return ResponseEntity.ok(sideEffectResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SideEffectResponse> getSideEffect(@PathVariable String id) {
    SideEffectResponse sideEffectResponse = sideEffectService.getSideEffect(id);
    return ResponseEntity.ok(sideEffectResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SideEffectResponse> updateSideEffect(
      @PathVariable String id, @RequestBody UpdateSideEffectRequest request) {
    SideEffectResponse sideEffectResponse = sideEffectService.updateSideEffect(id, request);
    return ResponseEntity.ok(sideEffectResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSideEffect(@PathVariable String id) {
    sideEffectService.deleteSideEffect(id);
    return ResponseEntity.noContent().build();
  }
}
