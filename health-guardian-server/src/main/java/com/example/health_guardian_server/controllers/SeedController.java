package com.example.health_guardian_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.services.SeedService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/seeds")
@AllArgsConstructor
public class SeedController {

    final SeedService seedService;

    @PostMapping("/clear")
    public ResponseEntity<?> clear() {
        seedService.clear();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/default")
    public ResponseEntity<?> defaultSeed() {
        seedService.defaultSeed();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mock")
    public ResponseEntity<?> mockSeed() {
        seedService.mockSeed();
        return ResponseEntity.ok().build();
    }
}
