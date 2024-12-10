package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.entities.enums.*;
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
  AllergyRepository allergyRepository;
  AppointmentRepository appointmentRepository;
  DiagnosticReportRepository diagnosticReportRepository;
  DiagnosticResultRepository diagnosticResultRepository;
  ExternalProviderRepository externalProviderRepository;
  FamilyHistoryRepository familyHistoryRepository;
  HouseholdMemberRepository householdMemberRepository;
  HouseholdRepository householdRepository;
  ImmunizationRepository immunizationRepository;
  LocalProviderRepository localProviderRepository;
  NotificationRepository notificationRepository;
  PastConditionRepository pastConditionRepository;
  PhysicianNoteRepository physicianNoteRepository;
  PrescriptionRepository prescriptionRepository;
  PrescriptionItemRepository prescriptionItemRepository;
  SurgeryRepository surgeryRepository;
  UserRepository userRepository;
  VisitSummaryRepository visitSummaryRepository;
  PasswordEncoder passwordEncoder;

  Faker faker = new Faker();
  Random random = new Random();

  @Override
  @Transactional
  public void clear() {

    householdMemberRepository.deleteAllInBatch();
    householdRepository.deleteAllInBatch();
    visitSummaryRepository.deleteAllInBatch();
    appointmentRepository.deleteAllInBatch();
    prescriptionItemRepository.deleteAllInBatch();
    prescriptionRepository.deleteAllInBatch();
    diagnosticResultRepository.deleteAllInBatch();
    diagnosticReportRepository.deleteAllInBatch();
    physicianNoteRepository.deleteAllInBatch();
    surgeryRepository.deleteAllInBatch();
    allergyRepository.deleteAllInBatch();
    immunizationRepository.deleteAllInBatch();
    familyHistoryRepository.deleteAllInBatch();
    pastConditionRepository.deleteAllInBatch();
    notificationRepository.deleteAllInBatch();
    localProviderRepository.deleteAllInBatch();
    externalProviderRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();

    log.info("Database cleared");
  }

  @Override
  @Transactional
  public void initial() {
    User user = User.builder()
        .email("admin@health-guardian.com")
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

    LocalProvider localProvider = LocalProvider.builder()
        .email(user.getEmail())
        .user(user)
        .passwordHash(passwordEncoder.encode("Password@123"))
        .isVerified(true)
        .build();
    localProviderRepository.save(localProvider);
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
    for (int i = 0; i < users.size(); i++) {
      if (i % 3 == 0) {
        ExternalProvider externalProvider = ExternalProvider.builder()
            .providerName(faker.company().name())
            .user(users.get(i))
            .providerUserId(faker.idNumber().valid())
            .providerUserEmail(faker.internet().emailAddress())
            .token(faker.crypto().sha256())
            .build();
        externalProviders.add(externalProvider);
      } else {
        LocalProvider localProvider = LocalProvider.builder()
            .email(users.get(i).getEmail())
            .user(users.get(i))
            .passwordHash(passwordEncoder.encode("Password@123"))
            .isVerified(true)
            .build();
        localProviders.add(localProvider);
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

    // Allergies
    List<Allergy> allergies = new ArrayList<>();
    for (User x : users) {
      Allergy allergy = Allergy.builder()
          .user(x)
          .allergyName(faker.medical().diseaseName())
          .severity(faker.lorem().sentence(10))
          .reactionDescription(faker.lorem().sentence(20))
          .build();
      allergies.add(allergy);
    }
    allergyRepository.saveAll(allergies);

    // Surgery
    List<Surgery> surgeries = new ArrayList<>();
    for (User x : users) {
      Surgery surgery = Surgery.builder()
          .user(x)
          .date(faker.date().past(90, TimeUnit.DAYS))
          .description(faker.lorem().sentence(10))
          .notes(faker.lorem().sentence(20))
          .build();
      surgeries.add(surgery);
    }
    surgeryRepository.saveAll(surgeries);

    // PhysicianNote
    List<PhysicianNote> physicianNotes = new ArrayList<>();
    for (User x : users) {
      PhysicianNote physicianNote = PhysicianNote.builder()
          .user(x)
          .date(faker.date().past(50, TimeUnit.DAYS))
          .note(faker.medical().symptoms())
          .build();
      physicianNotes.add(physicianNote);
    }
    physicianNoteRepository.saveAll(physicianNotes);

    // DiagnosticReport
    List<DiagnosticReport> diagnosticReports = new ArrayList<>();
    for (User x : users) {
      DiagnosticReport diagnosticReport = DiagnosticReport.builder()
          .user(x)
          .reportDate(faker.date().past(60, TimeUnit.DAYS))
          .reportType(
              ReportType.class
                  .getEnumConstants()[new Random().nextInt(ReportType.class.getEnumConstants().length)])
          .summary(faker.lorem().sentence(20))
          .notes(faker.lorem().sentence(20))
          .build();
      diagnosticReports.add(diagnosticReport);
    }
    diagnosticReportRepository.saveAll(diagnosticReports);

    // DiagnosticResult
    List<DiagnosticResult> diagnosticResults = new ArrayList<>();
    for (User x : users) {
      DiagnosticResult diagnosticResult = DiagnosticResult.builder()
          .user(x)
          .testName(faker.medical().diseaseName())
          .resultDate(faker.date().past(50, TimeUnit.DAYS))
          .resultValue(faker.lorem().sentence(20))
          .notes(faker.lorem().sentence(20))
          .build();
      diagnosticResults.add(diagnosticResult);
    }
    diagnosticResultRepository.saveAll(diagnosticResults);

    // Prescription
    List<Prescription> prescriptions = new ArrayList<>();
    for (User x : users) {
      Prescription prescription = Prescription.builder()
          .user(x)
          .issuedBy("Doctor" + faker.name().fullName())
          .issuedDate(new Timestamp(faker.date().past(30, TimeUnit.DAYS).getTime()))
          .validUntil(faker.date().future(30, TimeUnit.DAYS))
          .status(
              PrescriptionStatus.class
                  .getEnumConstants()[new Random().nextInt(PrescriptionStatus.class.getEnumConstants().length)])
          .build();
      prescriptions.add(prescription);
    }
    prescriptionRepository.saveAll(prescriptions);

    // PrescriptionItem
    List<String> dosageEnum = Arrays.asList("500mg", "10ml", "200mg", "20ml");
    List<String> frequencyEnum = Arrays.asList("Twice/days", "Third/days", "Each 8 hours", "Each 24 hours");
    List<PrescriptionItem> prescriptionItems = new ArrayList<>();
    for (Prescription x : prescriptions) {
      for (int i = 0; i < 25; i++) {
        PrescriptionItem prescriptionItem = PrescriptionItem.builder()
            .prescription(x)
            .dosage(dosageEnum.get(random.nextInt(dosageEnum.size())))
            .medicationName(faker.medical().medicineName())
            .image(faker.internet().image())
            .frequency(frequencyEnum.get(random.nextInt(frequencyEnum.size())))
            .startDate(faker.date().past(50 + i, TimeUnit.DAYS))
            .endDate(faker.date().future(20 + i, TimeUnit.DAYS))
            .status(
                PrescriptionItemStatus.class
                    .getEnumConstants()[new Random()
                        .nextInt(PrescriptionItemStatus.class.getEnumConstants().length)])
            .build();
        prescriptionItems.add(prescriptionItem);
      }
    }
    prescriptionItemRepository.saveAll(prescriptionItems);

    // Appointment
    List<Appointment> appointments = new ArrayList<>();
    for (User x : users) {
      Appointment appointment = Appointment.builder()
          .user(x)
          .appoinmentDate(faker.date().future(30, TimeUnit.DAYS))
          .reason(faker.lorem().sentence(20))
          .address(faker.address().fullAddress())
          .status(
              AppointmentStatus.class
                  .getEnumConstants()[new Random().nextInt(AppointmentStatus.class.getEnumConstants().length)])
          .notes(faker.lorem().sentence(20))
          .build();
      appointments.add(appointment);
    }
    appointmentRepository.saveAll(appointments);

    // VisitSummary
    List<VisitSummary> visitSummaries = new ArrayList<>();
    for (User x : users) {
      VisitSummary visitSummary = VisitSummary.builder()
          .user(x)
          .visitDate(faker.date().past(30, TimeUnit.DAYS))
          .visitType(
              VisitSummaryType.class
                  .getEnumConstants()[new Random().nextInt(VisitSummaryType.class.getEnumConstants().length)])
          .summary(faker.lorem().sentence(20))
          .notes(faker.lorem().sentence(20))
          .build();
      visitSummaries.add(visitSummary);
    }
    visitSummaryRepository.saveAll(visitSummaries);

    // Household
    List<Household> households = new ArrayList<>();
    List<HouseholdMember> householdMembers = new ArrayList<>();
    for (int i = 0; i < users.size(); i += 5) {
      Household household = Household.builder()
          .name(users.get(i).getLastName() + users.get(i).getFirstName())
          .avatar(faker.avatar().image())
          .head(users.get(i))
          .build();
      households.add(household);

      for (int j = 1; j < 5; j++) {
        HouseholdMember householdMember = HouseholdMember.builder().household(household).user(users.get(i + j)).build();
        householdMembers.add(householdMember);
      }
    }
    householdRepository.saveAll(households);
    householdMemberRepository.saveAll(householdMembers);
  }
}
