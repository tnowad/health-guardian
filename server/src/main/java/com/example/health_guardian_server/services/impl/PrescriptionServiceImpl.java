package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.mappers.PrescriptionMapper;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
  private final PrescriptionRepository prescriptionRepository;

  private final UserRepository userRepository;

  private final PrescriptionMapper prescriptionMapper;


}
