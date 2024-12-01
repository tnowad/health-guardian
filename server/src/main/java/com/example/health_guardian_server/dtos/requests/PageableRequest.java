package com.example.health_guardian_server.dtos.requests;

import java.util.stream.IntStream;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface PageableRequest<T> {
  Integer getPage();

  Integer getSize();

  String[] getSortFields();

  Boolean[] getDesc();

  default Sort toSort() {
    return Sort.by(
        IntStream.range(0, getSortFields().length)
            .mapToObj(
                i ->
                    new Sort.Order(
                        i < getDesc().length && getDesc()[i]
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC,
                        getSortFields()[i]))
            .toList());
  }

  default Pageable toPageable() {
    return PageRequest.of(getPage(), getSize(), toSort());
  }

  Specification<T> toSpecification();
}
