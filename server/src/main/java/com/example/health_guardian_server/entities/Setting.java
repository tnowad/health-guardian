package com.example.health_guardian_server.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "settings")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Setting {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "key", nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private SettingKey key;

  @Column(name = "value", nullable = false)
  private String value;

  @Column(name = "description")
  private String description;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private SettingType type;

  public List<String> getStringArrayValue() {
    if (type == SettingType.STRING_ARRAY) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(value, new TypeReference<List<String>>() {});
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        return null;
      }
    }
    return null;
  }

  public void setStringArrayValue(List<String> stringArray) {
    if (type == SettingType.STRING_ARRAY) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        this.value = objectMapper.writeValueAsString(stringArray);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }
}
