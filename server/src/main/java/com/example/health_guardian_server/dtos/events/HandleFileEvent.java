package com.example.health_guardian_server.dtos.events;

import com.example.health_guardian_server.dtos.enums.FileMetadataType;
import com.example.health_guardian_server.dtos.enums.HandleFileAction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HandleFileEvent {

  String id;

  String objectKey;

  long size;

  String contentType;

  String ownerId;

  HandleFileAction action;

  FileMetadataType type;

}
