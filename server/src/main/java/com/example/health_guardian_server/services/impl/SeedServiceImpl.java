package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.entities.*;
import com.example.health_guardian_server.repositories.*;
import com.example.health_guardian_server.services.SeedService;
import com.example.health_guardian_server.utils.Constants;
import com.example.health_guardian_server.utils.Constants.PermissionName;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  @Override
  @Transactional
  public void clear() {
    settingRepository.deleteAllInBatch();
    permissionRepository.deleteAllInBatch();
    roleRepository.deleteAllInBatch();

    userMedicalStaffRepository.deleteAllInBatch();
    userPatientRepository.deleteAllInBatch();
    userStaffRepository.deleteAllInBatch();

    localProviderRepository.deleteAllInBatch();
    accountRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();

    aggregatedSideEffectRepository.deleteAllInBatch();
    appointmentRepository.deleteAllInBatch();
    consentFormRepository.deleteAllInBatch();
    externalProviderRepository.deleteAllInBatch();
    hospitalRepository.deleteAllInBatch();
    householdMemberRepository.deleteAllInBatch();
    householdRepository.deleteAllInBatch();
    medicationRepository.deleteAllInBatch();
    notificationRepository.deleteAllInBatch();
    patientLogRepository.deleteAllInBatch();
    patientRepository.deleteAllInBatch();
    guardianRepository.deleteAllInBatch();
    prescriptionRepository.deleteAllInBatch();
    reportedSideEffectRepository.deleteAllInBatch();
    sideEffectRepository.deleteAllInBatch();

    log.info("Database cleared");
  }

  @Override
  @Transactional
  public void initial() {
    List<Permission> permissions =
        List.copyOf(EnumSet.allOf(Constants.PermissionName.class)).stream()
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
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.PATIENT_UPDATE_OWN.name(),
            PermissionName.APPOINTMENT_VIEW_OWN.name(),
            PermissionName.PRESCRIPTION_VIEW_OWN.name(),
            PermissionName.REPORT_VIEW_OWN.name()));

    Map<String, List<String>> nurseRolePermissions = new HashMap<>();
    nurseRolePermissions.put(
        "nurse",
        Arrays.asList(
            PermissionName.PATIENT_VIEW_OWN.name(),
            PermissionName.NOTIFICATION_VIEW_OWN.name(),
            PermissionName.REPORT_VIEW_OWN.name()));

    permissionRoleMapping.add(anonymousRolePermissions);
    permissionRoleMapping.add(adminRolePermissions);
    permissionRoleMapping.add(doctorRolePermissions);
    permissionRoleMapping.add(nurseRolePermissions);

    List<Role> roles =
        permissionRoleMapping.stream()
            .map(
                rolePermissions -> {
                  Role role = new Role();
                  role.setName(rolePermissions.keySet().iterator().next());
                  role.setPermissions(
                      permissions.stream()
                          .filter(
                              permission ->
                                  rolePermissions
                                      .get(role.getName())
                                      .contains(permission.getName()))
                          .collect(Collectors.toSet()));
                  return role;
                })
            .collect(Collectors.toList());

    roleRepository.saveAll(roles);

    List<Setting> settings = new ArrayList<>();
    Setting roleSetting =
        Setting.builder()
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

    User adminUser =
        User.builder()
            .roles(
                adminRolePermissions.keySet().stream()
                    .map(
                        roleName ->
                            roles.stream()
                                .filter(role -> role.getName().equals(roleName))
                                .findFirst()
                                .get())
                    .collect(Collectors.toSet()))
            .build();
    userRepository.save(adminUser);

    Account adminAccount = Account.builder().user(adminUser).status(AccountStatus.ACTIVE).build();
    accountRepository.save(adminAccount);
    userRepository.save(adminUser);

    LocalProvider adminLocalProvider =
        LocalProvider.builder()
            .email("admin@health-guardian.com")
            .passwordHash(passwordEncoder.encode("Password@123"))
            .account(adminAccount)
            .build();
    localProviderRepository.save(adminLocalProvider);
  }

  @Override
  public void mock() {
//    List<Patient> patients = patientRepository.findAll();
//    List<UserMedicalStaff> medicalStaff = userMedicalStaffRepository.findAll();
//
//    for (Patient patient : patients) {
//      for (UserMedicalStaff doctor : medicalStaff) {
//        Appointment appointment =
//            new Appointment(
//                faker.idNumber().valid(),
//                patient,
//                doctor,
//                faker.date().future(30, TimeUnit.DAYS),
//                "Checkup",
//                AppointmentStatus.SCHEDULED);
//        appointmentRepository.save(appointment);
//      }
//    }
//    List<Medication> medications = new ArrayList<>();
//    for (int i = 0; i < 100; i++) {
//      String name = faker.medical().medicineName();
//      String activeIngredient = faker.medical().symptoms();
//      String dosageForm = faker.options().option("Tablet", "Capsule", "Injection", "Syrup");
//      String standardDosage = faker.number().numberBetween(10, 500) + " mg";
//      String manufacturer = faker.company().name();
//      medications.add(
//          Medication.builder()
//              .name(name)
//              .activeIngredient(activeIngredient)
//              .dosageForm(dosageForm)
//              .standardDosage(standardDosage)
//              .manufacturer(manufacturer)
//              .build());
//    }
//    medicationRepository.saveAll(medications);

    // Seed User with Faker
    for (int i = 0; i < 100; i++) {
      // Generate a random user type
      String userType = faker.options().option("Admin", "Moderator", "Medical", "Non-medical");

      // If the user is medical, we can link to a patient
      Patient linkedPatient = (userType.equals("Medical") || userType.equals("Non-medical"))
        ? patientRepository.findAll().get(faker.number().numberBetween(0, 99))
        : null;

      User user = User.builder()
        .userType(userType)
        .build();

      userRepository.save(user);

      // Create an account for the user
      if (userType.equals("Medical") || userType.equals("Non-medical")) {
        Account account = Account.builder()
          .user(user)
          .build();
        accountRepository.save(account);
      }

      // Optionally, link user to medical staff roles if medical user
      if (userType.equals("Medical")) {
        UserMedicalStaff medicalStaff = UserMedicalStaff.builder()
          .user(user)
          .hospital(hospitalRepository.findAll().get(faker.number().numberBetween(0, 99))) // Link to random hospital
          .staffType(faker.options().option("Doctor", "Nurse", "Technician"))
          .specialization(faker.medical().medicineName())
          .role(faker.options().option("Senior", "Junior", "Intern"))
          .active(faker.bool().bool())
          .build();
        userMedicalStaffRepository.save(medicalStaff);
      }
    }

    // Seed Guardians
    List<Guardian> guardians = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      guardians.add(Guardian.builder()
        .name(faker.name().fullName())
        .relationshipToPatient(faker.options().option("Parent", "Spouse", "Sibling"))
        .phone(faker.phoneNumber().cellPhone().replaceAll("[^\\d+]", ""))
        .email(faker.internet().emailAddress())
        .build());
    }
    guardianRepository.saveAll(guardians);

    // Seed Patients
    guardianRepository.findAll().forEach(guardian -> {
      for (int i = 0; i < 100; i++) {
        patientRepository.save(Patient.builder()
          .firstName(faker.name().firstName())
          .lastName(faker.name().lastName())
          .dob(Date.valueOf(LocalDate.now().minusYears(faker.number().numberBetween(1, 100))))
          .gender(faker.options().option("Male", "Female"))
          .status(MedicalStatus.values()[faker.number().numberBetween(0, MedicalStatus.values().length)])
          .guardian(guardian)
          .build());
      }
    });

    // Seed Medications
    for (int i = 0; i < 100; i++) {
      medicationRepository.save(Medication.builder()
        .name(faker.medical().medicineName())
        .activeIngredient(faker.medical().symptoms())
        .dosageForm(faker.options().option("Tablet", "Capsule", "Injection", "Syrup"))
        .standardDosage(faker.number().numberBetween(1, 500) + " mg")
        .manufacturer(faker.company().name())
        .build());
    }

    // Seed Prescriptions (linking patients and medications)
    patientRepository.findAll().forEach(patient -> {
      medicationRepository.findAll().forEach(medication -> {
        for (int i = 0; i < 100; i++) {
          prescriptionRepository.save(Prescription.builder()
            .patient(patient)
            .medication(medication)
            .prescribedBy(userRepository.findAll().get(faker.number().numberBetween(0, userRepository.findAll().size())))  // Assuming you have a list of doctor IDs to pull from
            .dosage(faker.number().numberBetween(1, 5) + " times a day")
            .frequency("Daily")
            .startDate(Date.valueOf(LocalDate.now().minusDays(faker.number().numberBetween(1, 30))))
            .endDate(Date.valueOf(LocalDate.now().plusDays(faker.number().numberBetween(1, 90))))
            .status(PrescriptionStatus.values()[faker.number().numberBetween(0, PrescriptionStatus.values().length)])
            .build());
        }
      });
    });
    // Seed Hospitals
    for (int i = 0; i < 100; i++) {
      Hospital hospital = Hospital.builder()
        .name(faker.company().name())
        .location(faker.address().fullAddress())
        .phone(faker.phoneNumber().phoneNumber())
        .email(faker.internet().emailAddress())
        .build();
      hospitalRepository.save(hospital);
    }

    // Seed User Medical Staffs
    userRepository.findAll().forEach(user -> {
      Hospital hospital = hospitalRepository.findAll().get(faker.number().numberBetween(0, 99));
      UserMedicalStaff userMedicalStaff = UserMedicalStaff.builder()
        .user(user)
        .hospital(hospital)
        .staffType(faker.options().option("Doctor", "Nurse", "Technician"))
        .specialization(faker.options().option("Pediatrics", "Cardiology", "Radiology"))
        .role(faker.job().title())
        .active(faker.bool().bool())
        .endDate(Date.valueOf(LocalDate.now().minusDays(faker.number().numberBetween(1, 365))))
        .build();
      userMedicalStaffRepository.save(userMedicalStaff);
    });

    // Seed Side Effects
    for (int i = 0; i < 100; i++) {
      SideEffect sideEffect = SideEffect.builder()
        .name(faker.medical().symptoms())
        .severity(SideEffectSeverity.values()[faker.number().numberBetween(0, SideEffectSeverity.values().length)])
        .description(faker.lorem().sentence())
        .build();
      sideEffectRepository.save(sideEffect);
    }

// Seed Reported Side Effects (linking patients, prescriptions, and side effects)
    patientRepository.findAll().forEach(patient -> {
      prescriptionRepository.findAll().forEach(prescription -> {
        for (int i = 0; i < 10; i++) { // Assuming each patient might report several side effects per prescription
          SideEffect sideEffect = sideEffectRepository.findAll().get(faker.number().numberBetween(0, 99));
          ReportedSideEffect reportedSideEffect = ReportedSideEffect.builder()
            .patient(patient)
            .sideEffect(sideEffect)
            .prescription(prescription)
            .reportDate(Date.valueOf(LocalDate.now().minusDays(faker.number().numberBetween(1, 365))))
            .severity(SideEffectSeverity.values()[faker.number().numberBetween(0, SideEffectSeverity.values().length)])
            .notes(faker.lorem().paragraph())
            .reportedBy(faker.name().fullName())
            .outcome(faker.options().option("Resolved", "Ongoing", "Worsened"))
            .build();
          reportedSideEffectRepository.save(reportedSideEffect);
        }
      });
    });
// Seed Households
    for (int i = 0; i < 100; i++) {
      Patient headPatient = patientRepository.findAll().get(faker.number().numberBetween(0, 99));
      Household household = Household.builder()
        .head(headPatient)
        .build();
      householdRepository.save(household);

      // Seed Household Members (each household with 1-5 members)
      for (int j = 0; j < faker.number().numberBetween(1, 5); j++) {
        Patient memberPatient = patientRepository.findAll().get(faker.number().numberBetween(0, 99));
        HouseholdMember householdMember = HouseholdMember.builder()
          .household(household)
          .patient(memberPatient)
          .relationshipToPatient(faker.options().option("Sibling", "Child", "Spouse"))
          .build();
        householdMemberRepository.save(householdMember);
      }
    }
// Seed Appointments (linking patients and doctors)
    patientRepository.findAll().forEach(patient -> {
      UserMedicalStaff doctor = userMedicalStaffRepository.findAll().get(faker.number().numberBetween(0, 99));
      for (int i = 0; i < 5; i++) { // Each patient has multiple appointments
        Appointment appointment = Appointment.builder()
          .patient(patient)
          .doctor(doctor)
          .appointmentDate(Date.valueOf(LocalDate.now().plusDays(faker.number().numberBetween(1, 30))))
          .reasonForVisit(faker.medical().diseaseName())
          .status(AppointmentStatus.values()[faker.number().numberBetween(0, AppointmentStatus.values().length)])
          .build();
        appointmentRepository.save(appointment);
      }
    });
// Seed Consent Forms
    patientRepository.findAll().forEach(patient -> {
      for (int i = 0; i < 5; i++) {
        ConsentForm consentForm = ConsentForm.builder()
          .patient(patient)
          .formName("Consent Form " + faker.number().numberBetween(1, 100))
          .consentDate(Date.valueOf(LocalDate.now().minusDays(faker.number().numberBetween(1, 365))))
          .status(ConsentStatus.values()[faker.number().numberBetween(0, ConsentStatus.values().length)])
          .build();
        consentFormRepository.save(consentForm);
      }
    });
// Seed Notifications
    userRepository.findAll().forEach(user -> {
      for (int i = 0; i < 5; i++) {
        Notification notification = Notification.builder()
          .user(user)
          .type(NotificationType.values()[faker.number().numberBetween(0, NotificationType.values().length)])
          .notificationDate(Timestamp.valueOf(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 365))))
          .readStatus(faker.bool().bool())
          .build();
        notificationRepository.save(notification);
      }
    });

  }
}
