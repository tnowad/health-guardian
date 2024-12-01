package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.entities.Medication;
import com.example.health_guardian_server.services.MedicationService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medications")
public class MedicationController {
  private final MedicationService medicationService;

  public MedicationController(MedicationService medicationService) {
    this.medicationService = medicationService;
  }

  // Define methods
  @GetMapping("/all")
  public ResponseEntity<List<Medication>> getAllMedications() {
    return new ResponseEntity<>(medicationService.getAllMedications(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Medication> getMedicationById(@PathVariable String id) {
    Medication medication = medicationService.getMedicationById(id);
    return new ResponseEntity<>(medication, HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<Medication>> getMedicationsByName(@PathVariable String name) {
    return new ResponseEntity<>(medicationService.getMedicationsByName(name), HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<Medication> createMedication(@RequestBody Medication medication) {
    Medication createdMedication = medicationService.createMedication(medication);
    return new ResponseEntity<>(createdMedication, HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<Medication> updateMedication(@RequestBody Medication medication) {
    Medication updatedMedication = medicationService.updateMedication(medication);
    return new ResponseEntity<>(updatedMedication, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteMedication(@PathVariable String id) {
    medicationService.deleteMedication(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
