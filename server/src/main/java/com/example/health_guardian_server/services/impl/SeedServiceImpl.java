package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.SeedService;
import com.example.health_guardian_server.utils.Constants;
import com.example.health_guardian_server.utils.Constants.PermissionName;
import com.github.javafaker.Faker;
import com.mifmif.common.regex.Generex;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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

  AccountRepository accountRepository;
  AggregatedSideEffectRepository aggregatedSideEffectRepository;
  AppointmentRepository appointmentRepository;
  ConsentFormRepository consentFormRepository;
  ExternalProviderRepository externalProviderRepository;
  GuardianRepository guardianRepository;
  HospitalRepository hospitalRepository;
  HouseholdMemberRepository householdMemberRepository;
  HouseholdRepository householdRepository;
  LocalProviderRepository localProviderRepository;
  MedicationRepository medicationRepository;
  NotificationRepository notificationRepository;
  PatientLogRepository patientLogRepository;
  PatientRepository patientRepository;
  PermissionRepository permissionRepository;
  PrescriptionRepository prescriptionRepository;
  ReportedSideEffectRepository reportedSideEffectRepository;
  RoleRepository roleRepository;
  SideEffectRepository sideEffectRepository;
  UserMedicalStaffRepository userMedicalStaffRepository;
  UserPatientRepository userPatientRepository;
  UserRepository userRepository;
  UserStaffRepository userStaffRepository;
  SettingRepository settingRepository;
  PasswordEncoder passwordEncoder;

  Faker faker = new Faker();
  Random random = new Random();

  @Override
  @Transactional
  public void clear() {
    settingRepository.deleteAllInBatch();
    permissionRepository.deleteAllInBatch();
    roleRepository.deleteAllInBatch();

    userPatientRepository.deleteAllInBatch();
    userStaffRepository.deleteAllInBatch();

    localProviderRepository.deleteAllInBatch();
    externalProviderRepository.deleteAllInBatch();
    accountRepository.deleteAllInBatch();

    aggregatedSideEffectRepository.deleteAllInBatch();
    appointmentRepository.deleteAllInBatch();
    userMedicalStaffRepository.deleteAllInBatch();
    consentFormRepository.deleteAllInBatch();
    hospitalRepository.deleteAllInBatch();
    householdMemberRepository.deleteAllInBatch();
    householdRepository.deleteAllInBatch();
    notificationRepository.deleteAllInBatch();
    patientLogRepository.deleteAllInBatch();
    reportedSideEffectRepository.deleteAllInBatch();
    prescriptionRepository.deleteAllInBatch();
    medicationRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    patientRepository.deleteAllInBatch();
    guardianRepository.deleteAllInBatch();
    sideEffectRepository.deleteAllInBatch();

    log.info("Database cleared");
  }

  @Override
  @Transactional
  public void initial() {
    List<Permission> permissions = List.copyOf(EnumSet.allOf(Constants.PermissionName.class)).stream()
        .map(permissionName -> Permission.builder().name(permissionName.name()).build())
        .collect(Collectors.toList());

    permissionRepository.saveAll(permissions);

    List<Map<String, List<String>>> permissionRoleMapping = new ArrayList<>();

    Map<String, List<String>> anonymousRolePermissions = new HashMap<>();
    anonymousRolePermissions.put(
        "anonymous",
        Arrays.asList(
            PermissionName.ACCOUNT_CREATE_OWN.name(),
            PermissionName.ACCOUNT_SIGNIN_OWN.name(),
            PermissionName.ACCOUNT_SIGNOUT_OWN.name()));

    Map<String, List<String>> adminRolePermissions = new HashMap<>();
    adminRolePermissions.put(
        "admin",
        Arrays.asList(
            PermissionName.VIEW_MAIN_DASHBOARD.name(),
            PermissionName.ACCOUNT_VIEW_OWN.name(),
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.APPOINTMENT_CREATE_OWN.name(),
            PermissionName.PRESCRIPTION_CREATE_OWN.name(),
            PermissionName.MEDICATION_ADD_OWN.name(),
            PermissionName.REPORT_CREATE_OWN.name()));

    Map<String, List<String>> doctorRolePermissions = new HashMap<>();
    doctorRolePermissions.put(
        "doctor",
        Arrays.asList(
            PermissionName.VIEW_MAIN_DASHBOARD.name(),
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.PATIENT_UPDATE_OWN.name(),
            PermissionName.APPOINTMENT_VIEW_OWN.name(),
            PermissionName.PRESCRIPTION_VIEW_OWN.name(),
            PermissionName.REPORT_VIEW_OWN.name()));

    Map<String, List<String>> nurseRolePermissions = new HashMap<>();
    nurseRolePermissions.put(
        "nurse",
        Arrays.asList(
            PermissionName.VIEW_MAIN_DASHBOARD.name(),
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.NOTIFICATION_VIEW_OWN.name(),
            PermissionName.REPORT_VIEW_OWN.name()));

    permissionRoleMapping.add(anonymousRolePermissions);
    permissionRoleMapping.add(adminRolePermissions);
    permissionRoleMapping.add(doctorRolePermissions);
    permissionRoleMapping.add(nurseRolePermissions);

    List<Role> roles = permissionRoleMapping.stream()
        .map(
            rolePermissions -> {
              Role role = new Role();
              role.setName(rolePermissions.keySet().iterator().next());
              role.setDescription(faker.lorem().sentence(10));
              role.setPermissions(
                  permissions.stream()
                      .filter(
                          permission -> rolePermissions
                              .get(role.getName())
                              .contains(permission.getName()))
                      .collect(Collectors.toSet()));
              return role;
            })
        .collect(Collectors.toList());

    roleRepository.saveAll(roles);

    List<Setting> settings = new ArrayList<>();
    Setting roleSetting = Setting.builder()
        .key(SettingKey.ROLE_DEFAULT_IDS)
        .description("Default role for new users")
        .type(SettingType.STRING_ARRAY)
        .build();

    roleSetting.setStringArrayValue(
        roles.stream()
            .filter(role -> role.getName().equals("anonymous"))
            .map(Role::getId)
            .collect(Collectors.toList()));

    settings.add(roleSetting);

    settingRepository.saveAll(settings);

    User adminUser = User.builder()
        .roles(
            adminRolePermissions.keySet().stream()
                .map(
                    roleName -> roles.stream()
                        .filter(role -> role.getName().equals(roleName))
                        .findFirst()
                        .get())
                .collect(Collectors.toSet()))
        .username("admin")
        .email("admin@health-guardian.com")
        .type(UserType.STAFF)
        .build();
    userRepository.save(adminUser);

    Account adminAccount = Account.builder().user(adminUser).status(AccountStatus.ACTIVE).build();
    accountRepository.save(adminAccount);
    userRepository.save(adminUser);

    LocalProvider adminLocalProvider = LocalProvider.builder()
        .email("admin@health-guardian.com")
        .passwordHash(passwordEncoder.encode("Password@123"))
        .account(adminAccount)
        .build();
    localProviderRepository.save(adminLocalProvider);

    User patientUser = User.builder()
        .roles(
            adminRolePermissions.keySet().stream()
                .map(
                    roleName -> roles.stream()
                        .filter(role -> role.getName().equals(roleName))
                        .findFirst()
                        .get())
                .collect(Collectors.toSet()))
        .username("patient")
        .email("patient@health-guardian.com")
        .type(UserType.PATIENT)
        .build();
    userRepository.save(patientUser);

    Account patientAccount = Account.builder().user(patientUser).status(AccountStatus.ACTIVE).build();
    accountRepository.save(patientAccount);
    userRepository.save(patientUser);

    LocalProvider patientLocalProvider = LocalProvider.builder()
        .email("patient@health-guardian.com")
        .passwordHash(passwordEncoder.encode("Password@123"))
        .account(patientAccount)
        .build();
    localProviderRepository.save(patientLocalProvider);

    Guardian guardian = Guardian.builder()
        .name("Jane Doe")
        .relationshipToPatient("Mother")
        .phone("0123456789")
        .email("mother@health-guardian.com")
        .build();

    Patient patient = Patient.builder()
        .firstName("John")
        .lastName("Doe")
        .dob(faker.date().birthday())
        .guardian(guardian)
        .gender("MALE")
        .status(MedicalStatus.HEALTHY)
        .createdAt(new Date())
        .updatedAt(new Date())
        .build();

    guardianRepository.save(guardian);
    patientRepository.save(patient);
    patientUser.setPatient(patient);
    userRepository.save(patientUser);

    log.info("Database initialized");
  }

  @Override
  public void mock() {
    // Guardian
    List<Guardian> guardians = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      Guardian guardian = Guardian.builder()
          .name(faker.name().fullName())
          .relationshipToPatient(faker.relationships().any())
          .phone(new Generex("0[1-9][0-9]{8}").random())
          .email(faker.internet().emailAddress())
          .build();
      guardians.add(guardian);
    }
    guardianRepository.saveAll(guardians);

    // Patient
    List<Patient> patients = new ArrayList<>();
    List<String> genders = Arrays.asList("Male", "Female");
    for (int i = 0; i < guardians.size(); i++) {
      Patient patient = Patient.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .dob(faker.date().past(300, TimeUnit.DAYS))
        .gender(genders.get(random.nextInt(genders.size())))
        .guardian(guardians.get(i))
        .status(MedicalStatus.values()[random.nextInt(MedicalStatus.values().length)])
        .createdAt(faker.date().past(390, TimeUnit.DAYS))
        .updatedAt(faker.date().past(380, TimeUnit.DAYS))
        .build();
      patients.add(patient);
    }
    for (int i = 0; i < 100; i++) {
      Patient patient = Patient.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .dob(faker.date().past(300, TimeUnit.DAYS))
        .gender(genders.get(random.nextInt(genders.size())))
        .guardian(null)
        .status(MedicalStatus.values()[random.nextInt(MedicalStatus.values().length)])
        .createdAt(faker.date().past(390, TimeUnit.DAYS))
        .updatedAt(faker.date().past(380, TimeUnit.DAYS))
        .build();
      patients.add(patient);
    }
    patientRepository.saveAll(patients);

    // PatientLog
    List<PatientLog> patientLogs = new ArrayList<>();
    for (int i = 0; i < patients.size(); i++) {
      for(int j = 0; j < 10; j++) {
        PatientLog patientLog = PatientLog.builder()
          .patient(patients.get(i))
          .logType(faker.lorem().sentence(5))
          .message(faker.lorem().sentence(10))
          .createdAt(faker.date().past(390, TimeUnit.DAYS))
          .build();
        patientLogs.add(patientLog);
      }
    }
    patientLogRepository.saveAll(patientLogs);

    // Hospital
    List<Hospital> hospitals = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      Hospital hospital = Hospital.builder()
          .name(faker.medical().hospitalName())
          .location(faker.address().fullAddress())
          .phone(new Generex("0[1-9][0-9]{8}").random())
          .email(faker.internet().emailAddress())
          .build();
      hospitals.add(hospital);
    }
    hospitalRepository.saveAll(hospitals);

    // Get Roles
    List<Role> roles = roleRepository.findAll();
    Set<Role> patientRole = new HashSet<>();
    Set<Role> adminRole = new HashSet<>();
    Set<Role> staffRole = new HashSet<>();
    for (Role x : roles) {
      if (x.getName().equals("anonymous")) {
        patientRole.add(x);
      } else if (x.getName().equals("admin")) {
        adminRole.add(x);
      } else if (x.getName().equals("nurse") || x.getName().equals("doctor")) {
        staffRole.add(x);
      }
    }
    roleRepository.saveAll(patientRole);
    roleRepository.saveAll(adminRole);
    roleRepository.saveAll(staffRole);

    // User
    List<User> users = userRepository.findAll();
    for (int i = 0; i < patients.size(); i++) {
      User user = User.builder()
          .username(faker.name().username() + i)
          .email(i + faker.internet().emailAddress())
          .type(UserType.PATIENT)
          .patient(patients.get(i))
          .roles(patientRole)
          .build();
      users.add(user);
    }
    for (int i = 0; i < 100; i++) {
      User user = User.builder()
          .username(faker.name().username() + i)
          .email(i + faker.internet().emailAddress())
          .type(UserType.STAFF)
          .patient(null)
          .roles(adminRole)
          .build();
      users.add(user);
    }
    for (int i = 0; i < 100; i++) {
      User user = User.builder()
          .username(faker.name().username() + i)
          .email(i + faker.internet().emailAddress())
          .type(UserType.MEDICAL_STAFF)
          .patient(null)
          .roles(staffRole)
          .build();
      users.add(user);
    }
    userRepository.saveAll(users);

    // UserStaff, UserMedicalStaff
    List<UserStaff> userStaffs = new ArrayList<>();
    List<UserMedicalStaff> userMedicalStaffs = new ArrayList<>();
    for (User x : users) {
      if (x.getType().equals(UserType.STAFF)) {
        UserStaff userStaff = UserStaff.builder()
            .user(x)
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .dateOfBirth(faker.date().past(80, TimeUnit.DAYS))
            .role(faker.lorem().sentence(10))
            .roleType(faker.job().position())
            .build();
        userStaffs.add(userStaff);
      } else if (x.getType().equals(UserType.MEDICAL_STAFF)) {
        UserStaff userStaff = UserStaff.builder()
            .user(x)
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .dateOfBirth(faker.date().past(80, TimeUnit.DAYS))
            .role(faker.lorem().sentence(10))
            .roleType(faker.job().position())
            .build();
        userStaffs.add(userStaff);
        UserMedicalStaff userMedicalStaff = UserMedicalStaff.builder()
            .user(x)
            .hospital(hospitals.get(random.nextInt(hospitals.size())))
            .staffType(faker.job().field())
            .specialization(faker.lorem().sentence(10))
            .role(faker.job().position())
            .active(true)
            .endDate(faker.date().past(50, TimeUnit.DAYS))
            .build();
        userMedicalStaffs.add(userMedicalStaff);
      }
    }
    userStaffRepository.saveAll(userStaffs);
    userMedicalStaffRepository.saveAll(userMedicalStaffs);

    // Account
    List<Account> accounts = new ArrayList<>();
    for (User x : users) {
      if (x.getType().equals(UserType.PATIENT)) {
        Account account = Account.builder()
            .profileType("PatientProfile")
            .userId(x.getId())
            .user(x)
            .status(AccountStatus.ACTIVE)
            .build();
        accounts.add(account);
      } else if (x.getType().equals(UserType.STAFF)) {
        Account account = Account.builder()
            .profileType("StaffProfile")
            .userId(x.getId())
            .user(x)
            .status(AccountStatus.ACTIVE)
            .build();
        accounts.add(account);
      } else if (x.getType().equals(UserType.MEDICAL_STAFF)) {
        Account account = Account.builder()
            .profileType("MedicalStaffProfile")
            .userId(x.getId())
            .user(x)
            .status(AccountStatus.ACTIVE)
            .build();
        accounts.add(account);
      }
    }
    accountRepository.saveAll(accounts);

    // LocalProvider, ExternalProvider
    List<LocalProvider> localProviders = new ArrayList<>();
    List<ExternalProvider> externalProviders = new ArrayList<>();
    for(int i = 0; i < accounts.size()/2; i++) {
      LocalProvider localProvider = LocalProvider.builder()
        .email(accounts.get(i).getUser().getEmail())
        .passwordHash(passwordEncoder.encode("Password@123"))
        .account(accounts.get(i))
        .build();
      localProviders.add(localProvider);
    }
    for (int i = accounts.size()/2; i < accounts.size(); i++) {
      ExternalProvider externalProvider = ExternalProvider.builder()
        .providerUserId(faker.idNumber().valid())
        .providerName(faker.name().fullName())
        .providerUserEmail(accounts.get(i).getUser().getEmail())
        .account(accounts.get(i))
        .token(faker.crypto().sha256())
        .build();
      externalProviders.add(externalProvider);
    }
    localProviderRepository.saveAll(localProviders);
    externalProviderRepository.saveAll(externalProviders);

    // Notifications
    List<Notification> notifications = new ArrayList<>();
    for (int i = 0; i < users.size(); i++) {
      for (int j = 0; j < 10; j++) {
        Notification notification1 = Notification.builder()
          .user(users.get(i))
          .type(NotificationType.values()[random.nextInt(NotificationType.values().length)])
          .notificationDate(new Timestamp(faker.date().past(2, TimeUnit.DAYS).getTime()))
          .readStatus(false)
          .build();
        notifications.add(notification1);
      }
    }
    for (int i = 0; i < 10; i++) {
      Notification notification = Notification.builder()
          .user(users.get(random.nextInt(users.size())))
          .type(NotificationType.values()[random.nextInt(NotificationType.values().length)])
          .notificationDate(new Timestamp(faker.date().past(5, TimeUnit.DAYS).getTime()))
          .readStatus(true)
          .build();
      notifications.add(notification);
    }
    notificationRepository.saveAll(notifications);

    // Appointment
    List<Appointment> appointments = new ArrayList<>();
    for (int i = 0; i < patients.size(); i++) {
      for(int j = 0; j < 10; j++) {
        Appointment appointment = Appointment.builder()
          .patient(patients.get(i))
          .doctor(userMedicalStaffs.get(random.nextInt(userMedicalStaffs.size())))
          .appointmentDate(faker.date().future(10, TimeUnit.DAYS))
          .reasonForVisit(faker.lorem().sentence(10))
          .status(AppointmentStatus.SCHEDULED)
          .build();
        appointments.add(appointment);
      }
      for(int j = 0; j < 10; j++) {
        Appointment appointment = Appointment.builder()
          .patient(patients.get(i))
          .doctor(userMedicalStaffs.get(random.nextInt(userMedicalStaffs.size())))
          .appointmentDate(faker.date().future(10, TimeUnit.DAYS))
          .reasonForVisit(faker.lorem().sentence(10))
          .status(AppointmentStatus.COMPLETED)
          .build();
        appointments.add(appointment);
      }
      for(int j = 0; j < 10; j++) {
        Appointment appointment = Appointment.builder()
          .patient(patients.get(i))
          .doctor(userMedicalStaffs.get(random.nextInt(userMedicalStaffs.size())))
          .appointmentDate(faker.date().future(10, TimeUnit.DAYS))
          .reasonForVisit(faker.lorem().sentence(10))
          .status(AppointmentStatus.CANCELED)
          .build();
        appointments.add(appointment);
      }
    }
    appointmentRepository.saveAll(appointments);

    // ConsentForms
    List<ConsentForm> consentForms = new ArrayList<>();
    for (Patient x: patients) {
      for (int i = 0; i < 10; i++) {
        ConsentForm consentForm = ConsentForm.builder()
          .patient(patients.get(i))
          .formName(faker.name().title())
          .consentDate(faker.date().past(30, TimeUnit.DAYS))
          .status(ConsentStatus.GRANTED)
          .build();
        consentForms.add(consentForm);
      }
      for (int i = 0; i < 10; i++) {
        ConsentForm consentForm = ConsentForm.builder()
          .patient(patients.get(i))
          .formName(faker.name().title())
          .consentDate(faker.date().past(30, TimeUnit.DAYS))
          .status(ConsentStatus.REVOKED)
          .build();
        consentForms.add(consentForm);
      }
    }
    consentFormRepository.saveAll(consentForms);

    // Households
    List<Household> households = new ArrayList<>();
    List<HouseholdMember> householdMembers = new ArrayList<>();
    for (int i = 0; i < patients.size(); i += 10) {
      Household household = Household.builder().head(patients.get(i)).build();
      households.add(household);

      for (int j = 1; j <= 9; j++) {
        HouseholdMember householdMember = HouseholdMember.builder()
            .household(household)
            .patient(patients.get(i + j))
            .relationshipToPatient(faker.relationships().any())
            .build();
        householdMembers.add(householdMember);
      }
    }
    householdRepository.saveAll(households);
    householdMemberRepository.saveAll(householdMembers);

    // Medications
    List<Medication> medications = new ArrayList<>();
    List<String> dosageFormEnum = Arrays.asList("Tablet", "Capsule", "Solution", "Ointment");
    List<String> standardDosageEnum = Arrays.asList(
        "500mg per dose, taken twice a day.",
        "500mg per dose, taken third a day.",
        "1 tablet before meals.");
    for (int i = 0; i < 200; i++) {
      Medication medication = Medication.builder()
          .name(faker.medical().medicineName())
          .activeIngredient(faker.lorem().sentence(5))
          .dosageForm(dosageFormEnum.get(random.nextInt(dosageFormEnum.size())))
          .standardDosage(standardDosageEnum.get(random.nextInt(standardDosageEnum.size())))
          .manufacturer(faker.company().industry())
          .build();
      medications.add(medication);
    }
    medicationRepository.saveAll(medications);

    // Side-effects
    List<SideEffect> sideEffects = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      SideEffect sideEffect = SideEffect.builder()
        .name(faker.medical().diseaseName())
        .severity(
          SideEffectSeverity.values()[random.nextInt(SideEffectSeverity.values().length)])
        .description(faker.lorem().sentence(20))
        .build();
      sideEffects.add(sideEffect);
    }
    sideEffectRepository.saveAll(sideEffects);

    // Aggregated_side_effects
    List<AggregatedSideEffect> aggregatedSideEffects = new ArrayList<>();
    for (int i = 0; i < sideEffects.size(); i++) {
      for (int j = 0; j < 10; j++) {
        AggregatedSideEffect aggregatedSideEffect = AggregatedSideEffect.builder()
          .sideEffect(sideEffects.get(i))
          .medication(medications.get(random.nextInt(medications.size())))
          .totalReports(random.nextInt(0, 50))
          .periodStart(faker.date().past(50, TimeUnit.DAYS))
          .periodEnd(faker.date().future(30, TimeUnit.DAYS))
          .build();
        aggregatedSideEffects.add(aggregatedSideEffect);
      }
    }
    aggregatedSideEffectRepository.saveAll(aggregatedSideEffects);

    // Prescriptions, ReportSideEffect
    List<String> dosageEnum = Arrays.asList("500mg", "10ml", "200mg", "20ml");
    List<String> frequencyEnum = Arrays.asList("Twice/days", "Third/days", "Each 8 hours", "Each 24 hours");
    List<Prescription> prescriptions = new ArrayList<>();
    List<ReportedSideEffect> reportedSideEffects = new ArrayList<>();
    for(int i = 0; i < patients.size(); i++) {
      for (int j = 0; j < 10; j++) {
        Prescription prescription = new Prescription();
        prescription.setPatient(patients.get(i));
        prescription.setMedication(medications.get(random.nextInt(medications.size())));

        while (true) {
          User usertemp = users.get(random.nextInt(users.size()));
          if (usertemp.getType().equals(UserType.MEDICAL_STAFF)) {
            prescription.setPrescribedBy(usertemp);
            break;
          }
        }

        prescription.setDosage(dosageEnum.get(random.nextInt(dosageEnum.size())));
        prescription.setFrequency(frequencyEnum.get(random.nextInt(frequencyEnum.size())));
        prescription.setStartDate(faker.date().past(100, TimeUnit.DAYS));
        prescription.setEndDate(faker.date().future(50, TimeUnit.DAYS));
        prescription.setStatus(PrescriptionStatus.COMPLETED);

        prescriptions.add(prescription);

        ReportedSideEffect reportedSideEffect = ReportedSideEffect.builder()
          .patient(patients.get(i))
          .sideEffect(sideEffects.get(random.nextInt(sideEffects.size())))
          .prescription(prescription)
          .reportDate(faker.date().past(60, TimeUnit.DAYS))
          .severity(
            SideEffectSeverity.values()[random.nextInt(SideEffectSeverity.values().length)])
          .notes(faker.lorem().sentence(10))
          .reportedBy(faker.name().fullName())
          .outcome(faker.lorem().sentence(20))
          .build();
        reportedSideEffects.add(reportedSideEffect);
      }
      for (int j = 0; j < 10; j++) {
        Prescription prescription = new Prescription();
        prescription.setPatient(patients.get(i));
        prescription.setMedication(medications.get(random.nextInt(medications.size())));

        while (true) {
          User usertemp = users.get(random.nextInt(users.size()));
          if (usertemp.getType().equals(UserType.MEDICAL_STAFF)) {
            prescription.setPrescribedBy(usertemp);
            break;
          }
        }

        prescription.setDosage(dosageEnum.get(random.nextInt(dosageEnum.size())));
        prescription.setFrequency(frequencyEnum.get(random.nextInt(frequencyEnum.size())));
        prescription.setStartDate(faker.date().past(50, TimeUnit.DAYS));
        prescription.setEndDate(faker.date().future(50, TimeUnit.DAYS));
        prescription.setStatus(PrescriptionStatus.ACTIVE);

        prescriptions.add(prescription);

        ReportedSideEffect reportedSideEffect = ReportedSideEffect.builder()
          .patient(patients.get(i))
          .sideEffect(sideEffects.get(random.nextInt(sideEffects.size())))
          .prescription(prescription)
          .reportDate(faker.date().past(60, TimeUnit.DAYS))
          .severity(
            SideEffectSeverity.values()[random.nextInt(SideEffectSeverity.values().length)])
          .notes(faker.lorem().sentence(10))
          .reportedBy(faker.name().fullName())
          .outcome(faker.lorem().sentence(20))
          .build();
        reportedSideEffects.add(reportedSideEffect);
      }
      for (int j = 0; j < 10; j++) {
        Prescription prescription = new Prescription();
        prescription.setPatient(patients.get(i));
        prescription.setMedication(medications.get(random.nextInt(medications.size())));

        while (true) {
          User usertemp = users.get(random.nextInt(users.size()));
          if (usertemp.getType().equals(UserType.MEDICAL_STAFF)) {
            prescription.setPrescribedBy(usertemp);
            break;
          }
        }

        prescription.setDosage(dosageEnum.get(random.nextInt(dosageEnum.size())));
        prescription.setFrequency(frequencyEnum.get(random.nextInt(frequencyEnum.size())));
        prescription.setStartDate(faker.date().past(90, TimeUnit.DAYS));
        prescription.setEndDate(null);
        prescription.setStatus(PrescriptionStatus.EXPIRED);

        prescriptions.add(prescription);

        ReportedSideEffect reportedSideEffect = ReportedSideEffect.builder()
          .patient(patients.get(i))
          .sideEffect(sideEffects.get(random.nextInt(sideEffects.size())))
          .prescription(prescription)
          .reportDate(faker.date().past(60, TimeUnit.DAYS))
          .severity(
            SideEffectSeverity.values()[random.nextInt(SideEffectSeverity.values().length)])
          .notes(faker.lorem().sentence(10))
          .reportedBy(faker.name().fullName())
          .outcome(faker.lorem().sentence(20))
          .build();
        reportedSideEffects.add(reportedSideEffect);
      }
    }
    prescriptionRepository.saveAll(prescriptions);
    reportedSideEffectRepository.saveAll(reportedSideEffects);

    // UserPatient
    List<UserPatient> userPatients = new ArrayList<>();
    for (int i = 0; i < patients.size(); i++) {
      UserPatient userPatient = new UserPatient();
      userPatient.setPatient(patients.get(random.nextInt(patients.size())));

      while (true) {
        User usertemp = users.get(random.nextInt(users.size()));
        if (usertemp.getType().equals(UserType.PATIENT)) {
          userPatient.setUser(usertemp);
          break;
        }
      }

      userPatients.add(userPatient);
    }
    userPatientRepository.saveAll(userPatients);
  }
}
