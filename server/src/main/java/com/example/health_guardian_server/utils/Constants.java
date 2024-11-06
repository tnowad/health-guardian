package com.example.health_guardian_server.utils;

import com.nimbusds.jose.JWSAlgorithm;

import static com.nimbusds.jose.JWSAlgorithm.HS384;
import static com.nimbusds.jose.JWSAlgorithm.HS512;

public class Constants {

  public static String MICROSERVICE_NAME = "IDENTITY-SERVICE";

  public static final ThreadLocal<String> REST_AUTHORIZATION_CONTEXT = new ThreadLocal<>();

  public static final JWSAlgorithm ACCESS_TOKEN_SIGNATURE_ALGORITHM = HS512;

  public static final JWSAlgorithm REFRESH_TOKEN_SIGNATURE_ALGORITHM = HS384;

  public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static final String KAFKA_TOPIC_CREATE_PROFILE = "CREATE_PROFILE";

  public static final String KAFKA_TOPIC_SEND_MAIL = "SEND_MAIL";

}
