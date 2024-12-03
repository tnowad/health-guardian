package com.example.health_guardian_server.exceptions.file;

import lombok.Getter;

@Getter
public enum FileErrorCode {
  NO_FILE_PROVIDED("file/no-file-provided", "no_file_provided"),
  INVALID_FILE_PROVIDED("file/invalid-file-provided", "invalid_file_provided"),
  INVALID_FILE_TYPE("file/invalid-file-type", "invalid_file_type"),

  EMPTY_FILE("file/empty-file", "empty_file"),
  FILE_TOO_LARGE("file/file-too-large", "file_too_large"),
  CAN_NOT_STORE_FILE("file/can-not-store-file", "can_not_store_file"),
  COULD_NOT_READ_FILE("file/could-not-read-file", "could_not_read_file"),
  CAN_NOT_INIT_BACKUP_FOLDER("media/can-not-init-backup-folder", "can_not_init_backup_folder"),
  FILE_NOT_FOUND("file/file-not-found", "file_not_found"),
  FOLDER_NOT_FOUND("file/folder-not-found", "folder_not_found"),
  CAN_NOT_DELETE_FILE("file/can-not-delete-file", "can_not_delete_file"),
  CAN_NOT_DELETE_FOLDER("file/can-not-delete-folder", "can_not_delete_folder"),
  CHUNK_ALREADY_EXISTS("file/chunk-already-exists", "chunk_already_exists"),
  CHUNK_MISSING("file/chunk-missing", "chunk_missing"),
  COULD_NOT_WRITE_CHUNK("file/could-not-write-chunk", "could_not_write_chunk"),
  COULD_NOT_COMBINE_CHUNKS("file/could-not-combine-chunks", "could_not_combine_chunks"),
  CAN_NOT_CHECK_BUCKET("file/can-not-check-bucket", "can_not_check_bucket"),
  ;

  FileErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  private final String code;
  private final String message;
}
