package com.example.health_guardian_server.dtos.requests;

public interface PageableWithIdsRequest<T> {
  T[] getIds();
}
