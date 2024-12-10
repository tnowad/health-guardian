package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.SeedService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import java.util.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SeedServiceImpl implements SeedService {
  AppointmentRepository appointmentRepository;
  ExternalProviderRepository externalProviderRepository;
  HouseholdMemberRepository householdMemberRepository;
  HouseholdRepository householdRepository;
  LocalProviderRepository localProviderRepository;
  NotificationRepository notificationRepository;
  PrescriptionRepository prescriptionRepository;
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;

  Faker faker = new Faker();
  Random random = new Random();

  @Override
  @Transactional
  public void clear() {

    localProviderRepository.deleteAllInBatch();
    externalProviderRepository.deleteAllInBatch();

    appointmentRepository.deleteAllInBatch();
    householdMemberRepository.deleteAllInBatch();
    householdRepository.deleteAllInBatch();
    notificationRepository.deleteAllInBatch();
    prescriptionRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();

    log.info("Database cleared");
  }

  @Override
  @Transactional
  public void initial() {}

  @Override
  public void mock() {}
}
