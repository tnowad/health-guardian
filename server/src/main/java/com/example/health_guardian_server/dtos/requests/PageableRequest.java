package com.example.health_guardian_server.dtos.requests;

import java.util.stream.IntStream;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

interface PageableWithIdsRequest<T> {
  T[] getIds();
}

public interface PageableRequest<T> {
  Integer getPage();

  Integer getSize();

  String[] getSortFields();

  String[] getIds();

  Boolean[] getDesc();

  default Sort toSort() {
    return Sort.by(
        IntStream.range(0, getSortFields().length)
            .mapToObj(
                i -> new Sort.Order(
                    i < getDesc().length && getDesc()[i]
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                    getSortFields()[i]))
            .toList());
  }

  default Pageable toPageable() {

    if (this instanceof PageableWithIdsRequest) {
      if (this.getIds() != null && this.getIds().length > 0) {
        return PageRequest.of(0, getIds().length, toSort());
      }
    }
    return PageRequest.of(getPage(), getSize(), toSort());
  }

  Specification<T> toSpecification();
}
