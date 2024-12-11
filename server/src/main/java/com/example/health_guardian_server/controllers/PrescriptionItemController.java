package com.example.health_guardian_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.health_guardian_server.mappers.PrescriptionItemMapper;
import com.example.health_guardian_server.services.PrescriptionItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prescription-items")
@RequiredArgsConstructor
public class PrescriptionItemController {
  private final PrescriptionItemService prescriptionItemService;
  private final PrescriptionItemMapper prescriptionItemMapper;
}
