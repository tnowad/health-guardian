package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.requests.ListHouseholdMembersRequest;
import com.example.health_guardian_server.dtos.requests.UpdateHouseholdMemberRequest;
import com.example.health_guardian_server.dtos.responses.HouseholdMemberResponse;
import com.example.health_guardian_server.services.HouseholdMemberService;
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
@RequestMapping("/household-members")
@RequiredArgsConstructor
public class HouseholdMemberController {

  private final HouseholdMemberService householdMemberService;

  @GetMapping
  public ResponseEntity<Page<HouseholdMemberResponse>> listHouseholdMembers(
      @ModelAttribute ListHouseholdMembersRequest request) {
    Page<HouseholdMemberResponse> response = householdMemberService.listHouseholdMembers(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<HouseholdMemberResponse> createHouseholdMember(
      @RequestBody CreateHouseholdMemberRequest request) {
    HouseholdMemberResponse response = householdMemberService.createHouseholdMember(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{householdMemberId}")
  public ResponseEntity<HouseholdMemberResponse> getHouseholdMember(
      @PathVariable String householdMemberId) {
    HouseholdMemberResponse response = householdMemberService.getHouseholdMember(householdMemberId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{householdMemberId}")
  public ResponseEntity<HouseholdMemberResponse> updateHouseholdMember(
      @PathVariable String householdMemberId, @RequestBody CreateHouseholdMemberRequest request) {
    HouseholdMemberResponse response =
        householdMemberService.updateHouseholdMember(householdMemberId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{householdMemberId}")
  public ResponseEntity<Void> deleteHouseholdMember(@PathVariable String householdMemberId) {
    householdMemberService.deleteHouseholdMember(householdMemberId);
    return ResponseEntity.noContent().build();
  }
}
