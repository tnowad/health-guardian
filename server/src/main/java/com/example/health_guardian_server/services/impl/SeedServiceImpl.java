package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.entities.enums.GenderType;
import com.example.health_guardian_server.entities.enums.NotificationType;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.SeedService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
  FamilyHistoryRepository familyHistoryRepository;
  HouseholdMemberRepository householdMemberRepository;
  HouseholdRepository householdRepository;
  ImmunizationRepository immunizationRepository;
  LocalProviderRepository localProviderRepository;
  NotificationRepository notificationRepository;
  PastConditionRepository pastConditionRepository;
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
  public void initial() {
    User user = User.builder()
        .email("initialUser@gmail.com")
        .avatar(faker.avatar().image())
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .dob(faker.date().birthday())
        .gender(
            GenderType.class
                .getEnumConstants()[new Random().nextInt(GenderType.class.getEnumConstants().length)])
        .address(faker.address().fullAddress())
        .build();
    userRepository.save(user);
  }

  @Override
  public void mock() {

    // User
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      User user = User.builder()
          .email(faker.internet().emailAddress())
          .avatar(faker.avatar().image())
          .firstName(faker.name().firstName())
          .lastName(faker.name().lastName())
          .dob(faker.date().birthday())
          .gender(
              GenderType.class
                  .getEnumConstants()[new Random().nextInt(GenderType.class.getEnumConstants().length)])
          .address(faker.address().fullAddress())
          .build();
      users.add(user);
    }
    userRepository.saveAll(users);

    // External & Local Provider
    List<ExternalProvider> externalProviders = new ArrayList<>();
    List<LocalProvider> localProviders = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      if (i % 3 == 0) {
        LocalProvider localProvider = LocalProvider.builder()
            .email(users.get(i).getEmail())
            .user(users.get(i))
            .passwordHash(passwordEncoder.encode("Password@123"))
            .isVerified(true)
            .build();
        localProviders.add(localProvider);
      } else {
        ExternalProvider externalProvider = ExternalProvider.builder()
            .providerName(faker.company().name())
            .user(users.get(i))
            .providerUserId(faker.idNumber().valid())
            .providerUserEmail(faker.internet().emailAddress())
            .token(faker.crypto().sha256())
            .build();
        externalProviders.add(externalProvider);
      }
    }
    localProviderRepository.saveAll(localProviders);
    externalProviderRepository.saveAll(externalProviders);

    // Notification
    List<Notification> notifications = new ArrayList<>();
    for (User x : users) {
      for (int i = 0; i < 25; i++) {
        Notification notification = Notification.builder()
            .user(x)
            .notificationType(
                NotificationType.class
                    .getEnumConstants()[new Random().nextInt(NotificationType.class.getEnumConstants().length)])
            .notificationDate(
                new Timestamp(
                    faker
                        .date()
                        .between(new Date(2024 - 1900, 0, 1), new Date(2024 - 1900, 11, 31))
                        .getTime()))
            .readStatus(false)
            .build();
        notifications.add(notification);
      }
    }
    notificationRepository.saveAll(notifications);

    // PassCondition
    List<PastCondition> pastConditions = new ArrayList<>();
    for (User x : users) {
      for (int i = 0; i < 25; i++) {
        PastCondition pastCondition = PastCondition.builder()
            .user(x)
            .condition(faker.lorem().sentence(10))
            .description(faker.medical().symptoms())
            .dateDiagnosed(faker.date().past(100, TimeUnit.DAYS))
            .build();
        pastConditions.add(pastCondition);
      }
    }
    pastConditionRepository.saveAll(pastConditions);

    // Family History
    List<FamilyHistory> familyHistories = new ArrayList<>();
    for (User x : users) {
      FamilyHistory familyHistory = FamilyHistory.builder()
          .user(x)
          .relation(faker.relationships().any())
          .condition(faker.lorem().sentence(10))
          .description(faker.lorem().sentence(20))
          .build();
      familyHistories.add(familyHistory);
    }
    familyHistoryRepository.saveAll(familyHistories);

    // Immunization
    List<Immunization> immunizations = new ArrayList<>();
    for (User x : users) {
      Immunization immunization = Immunization.builder()
          .user(x)
          .vaccinationDate(faker.date().past(60, TimeUnit.DAYS))
          .vaccineName(faker.medical().medicineName())
          .batchNumber(faker.idNumber().valid())
          .notes(faker.lorem().sentence(20))
          .build();
      immunizations.add(immunization);
    }
    immunizationRepository.saveAll(immunizations);
  }
}
