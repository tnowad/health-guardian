package com.example.health_guardian_server.utils;

import static com.nimbusds.jose.JWSAlgorithm.HS384;
import static com.nimbusds.jose.JWSAlgorithm.HS512;

import com.nimbusds.jose.JWSAlgorithm;

public class Constants {

  public static String MICROSERVICE_NAME = "IDENTITY-SERVICE";

  public static final ThreadLocal<String> REST_AUTHORIZATION_CONTEXT = new ThreadLocal<>();

  public static final JWSAlgorithm ACCESS_TOKEN_SIGNATURE_ALGORITHM = HS512;

  public static final JWSAlgorithm REFRESH_TOKEN_SIGNATURE_ALGORITHM = HS384;

  public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static final String KAFKA_TOPIC_CREATE_PROFILE = "CREATE_PROFILE";

  public static final String KAFKA_TOPIC_SEND_MAIL = "SEND_MAIL";

  public static enum PermissionName {
    ACCOUNT_VIEW_OWN,
    ACCOUNT_UPDATE_OWN,
    ACCOUNT_DELETE_OWN,
    ACCOUNT_VIEW_ANY,
    ACCOUNT_UPDATE_ANY,
    ACCOUNT_DELETE_ANY,
    ACCOUNT_CREATE,

    ACCOUNT_CREATE_OWN,
    ACCOUNT_SIGNIN_OWN,
    ACCOUNT_SIGNOUT_OWN,

    PATIENT_VIEW_OWN,
    PATIENT_UPDATE_OWN,
    PATIENT_DELETE_OWN,
    PATIENT_VIEW_ANY,
    PATIENT_UPDATE_ANY,
    PATIENT_DELETE_ANY,
    PATIENT_CREATE,

    APPOINTMENT_VIEW_OWN,
    APPOINTMENT_CREATE_OWN,
    APPOINTMENT_UPDATE_OWN,
    APPOINTMENT_CANCEL_OWN,
    APPOINTMENT_VIEW_ANY,
    APPOINTMENT_CREATE_ANY,
    APPOINTMENT_UPDATE_ANY,
    APPOINTMENT_CANCEL_ANY,

    PRESCRIPTION_VIEW_OWN,
    PRESCRIPTION_CREATE_OWN,
    PRESCRIPTION_UPDATE_OWN,
    PRESCRIPTION_DELETE_OWN,
    PRESCRIPTION_VIEW_ANY,
    PRESCRIPTION_CREATE_ANY,
    PRESCRIPTION_UPDATE_ANY,
    PRESCRIPTION_DELETE_ANY,

    MEDICATION_VIEW_OWN,
    MEDICATION_ADD_OWN,
    MEDICATION_UPDATE_OWN,
    MEDICATION_DELETE_OWN,
    MEDICATION_VIEW_ANY,
    MEDICATION_ADD_ANY,
    MEDICATION_UPDATE_ANY,
    MEDICATION_DELETE_ANY,

    REPORT_VIEW_OWN,
    REPORT_CREATE_OWN,
    REPORT_UPDATE_OWN,
    REPORT_DELETE_OWN,
    REPORT_VIEW_ANY,
    REPORT_CREATE_ANY,
    REPORT_UPDATE_ANY,
    REPORT_DELETE_ANY,

    SIDE_EFFECT_VIEW_OWN,
    SIDE_EFFECT_REPORT_OWN,
    SIDE_EFFECT_VIEW_ANY,
    SIDE_EFFECT_REPORT_ANY,

    NOTIFICATION_VIEW_OWN,
    NOTIFICATION_MARK_READ_OWN,
    NOTIFICATION_VIEW_ANY,
    NOTIFICATION_MARK_READ_ANY,

    HOSPITAL_VIEW_OWN,
    HOSPITAL_UPDATE_OWN,
    HOSPITAL_DELETE_OWN,
    HOSPITAL_VIEW_ANY,
    HOSPITAL_UPDATE_ANY,
    HOSPITAL_DELETE_ANY,

    USER_VIEW_OWN,
    USER_UPDATE_OWN,
    USER_DELETE_OWN,
    USER_VIEW_ANY,
    USER_UPDATE_ANY,
    USER_DELETE_ANY,
    USER_CREATE,
    USER_GRANT_ROLE,
    USER_REVOKE_ROLE,

    GUARDIAN_VIEW_OWN,
    GUARDIAN_UPDATE_OWN,
    GUARDIAN_DELETE_OWN,
    GUARDIAN_VIEW_ANY,
    GUARDIAN_UPDATE_ANY,
    GUARDIAN_DELETE_ANY,

    CONSENT_VIEW_OWN,
    CONSENT_GRANT_OWN,
    CONSENT_REVOKE_OWN,
    CONSENT_VIEW_ANY,
    CONSENT_GRANT_ANY,
    CONSENT_REVOKE_ANY
  }
}
