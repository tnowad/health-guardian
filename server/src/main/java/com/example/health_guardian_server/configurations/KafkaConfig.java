package com.example.health_guardian_server.configurations;

import static com.example.health_guardian_server.utils.Constants.KAFKA_TOPIC_HANDLE_FILE;
import static com.example.health_guardian_server.utils.Constants.KAFKA_TOPIC_SEND_MAIL;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.health_guardian_server.dtos.events.HandleFileEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String BOOTSTRAP_SERVERS;

  @Value("${spring.kafka.mail-consumer.group-id}")
  private String SEND_MAIL_GROUP;

  @Value("${spring.kafka.file-consumer.group-id}")
  private String FILE_STORAGE_GROUP;

  private static final String CLIENT_ID = "sports-field-booking";

  /*
   * _________________________________________________TOPICS-
   * CONFIG________________________________________________________
   */
  @Bean
  public NewTopic handleFileTopic() {
    return createTopic(KAFKA_TOPIC_HANDLE_FILE);
  }

  @Bean
  public NewTopic sendMailTopic() {
    return createTopic(KAFKA_TOPIC_SEND_MAIL);
  }

  protected NewTopic createTopic(String topicName) {
    return TopicBuilder.name(topicName)
        .partitions(3)
        .replicas(1)
        .build();
  }

  /*
   * _________________________________________________CONTAINER-
   * FACTORIES________________________________________________________
   */
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> sendMailFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.getContainerProperties().setObservationEnabled(true);
    factory.setConsumerFactory(sendMailConsumer());
    factory.setConcurrency(3);
    factory.getContainerProperties().setClientId(CLIENT_ID);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, HandleFileEvent> handleFileContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, HandleFileEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.getContainerProperties().setObservationEnabled(true);
    factory.setConsumerFactory(handleFileConsumer());
    factory.setConcurrency(3);
    factory.getContainerProperties().setClientId(CLIENT_ID);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    factory.getContainerProperties().setObservationEnabled(true);
    return factory;
  }

  /*
   * _________________________________________________PRODUCER-
   * FACTORIES________________________________________________________
   */
  @Bean
  public ProducerFactory<String, HandleFileEvent> handleFileProducer() {
    Map<String, Object> props = new HashMap<>(producerCommonConfigs());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, HandleFileEvent> handleFileTemplate() {
    return new KafkaTemplate<>(handleFileProducer());
  }

  @Bean
  public ProducerFactory<String, String> sendMailProducer() {
    Map<String, Object> props = new HashMap<>(producerCommonConfigs());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, String> sendMailTemplate() {
    return new KafkaTemplate<>(sendMailProducer());
  }

  /*
   * _________________________________________________CONSUMER-
   * FACTORIES________________________________________________________
   */
  @Bean
  public ConsumerFactory<String, String> sendMailConsumer() {
    Map<String, Object> props = new HashMap<>(consumerCommonConfigs());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, SEND_MAIL_GROUP);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConsumerFactory<String, HandleFileEvent> handleFileConsumer() {
    Map<String, Object> props = new HashMap<>(consumerCommonConfigs());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, FILE_STORAGE_GROUP);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  /*
   * _________________________________________________ERROR-
   * HANDLERS________________________________________________________
   */
  @Bean
  public KafkaListenerErrorHandler kafkaListenerErrorHandler() {
    return (message, exception) -> {
      // Log the error and perform custom actions
      log.error("Error processing Kafka message: {}", message.getPayload(), exception);
      throw new RuntimeException("Error processing Kafka message", exception);
    };
  }

  /*
   * _________________________________________________COMMON-
   * PROPERTIES________________________________________________________
   */
  private Map<String, Object> consumerCommonConfigs() {
    Map<String, Object> props = new HashMap<>(commonConfigs());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }

  private Map<String, Object> producerCommonConfigs() {
    Map<String, Object> props = new HashMap<>(commonConfigs());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return props;
  }

  private Map<String, Object> commonConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    props.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");
    return props;
  }

}
