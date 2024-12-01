package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.dtos.requests.CreateHospitalRequest;
import com.example.health_guardian_server.dtos.responses.HospitalResponse;
import com.example.health_guardian_server.entities.Hospital;
import com.example.health_guardian_server.services.HospitalService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {
  private final HospitalService hospitalService;

  public HospitalController(HospitalService hospitalService) {
    this.hospitalService = hospitalService;
  }

  // Define methods

  @GetMapping("/all")
  public ResponseEntity<Page<HospitalResponse>> getAllHospitals(@RequestParam (defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Page<HospitalResponse> hospitals = hospitalService.getAllHospitals(page, size);

    return new ResponseEntity<>(hospitals, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<HospitalResponse> getHospitalById(String id) {
    HospitalResponse hospital = hospitalService.getHospitalById(id);
    return new ResponseEntity<>(hospital, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<HospitalResponse> createHospital(@RequestBody CreateHospitalRequest hospital) {
    HospitalResponse createdHospital = hospitalService.createHospital(hospital);
    return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<HospitalResponse> updateHospital(String id, @RequestBody HospitalResponse hospital) {
    HospitalResponse updatedHospital = hospitalService.updateHospital(id, hospital);
    return new ResponseEntity<>(updatedHospital, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteHospital(String id) {
    hospitalService.deleteHospital(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
