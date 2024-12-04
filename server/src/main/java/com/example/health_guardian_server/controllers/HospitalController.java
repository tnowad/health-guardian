package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateHospitalRequest;
import com.example.health_guardian_server.dtos.requests.ListHospitalRequest;
import com.example.health_guardian_server.dtos.responses.HospitalResponse;
import com.example.health_guardian_server.entities.Hospital;
import com.example.health_guardian_server.services.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {
  private final HospitalService hospitalService;
  // Define methods

  @GetMapping
  public ResponseEntity<Page<HospitalResponse>> getAllHospitals(@ModelAttribute ListHospitalRequest request) {
    Page<HospitalResponse> hospitals = hospitalService.getAllHospitals(request);
    return new ResponseEntity<>(hospitals, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<HospitalResponse> getHospitalById(String id) {
    HospitalResponse hospital = hospitalService.getHospitalById(id);
    return new ResponseEntity<>(hospital, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<HospitalResponse> createHospital(@RequestBody CreateHospitalRequest hospital) {
    HospitalResponse createdHospital = hospitalService.createHospital(hospital);
    return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<HospitalResponse> updateHospital(String id, @RequestBody HospitalResponse hospital) {
    HospitalResponse updatedHospital = hospitalService.updateHospital(id, hospital);
    return new ResponseEntity<>(updatedHospital, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHospital(String id) {
    hospitalService.deleteHospital(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
