package com.clean.clean_architecture_example.adapter.out.persistence.jpa;

import com.clean.clean_architecture_example.adapter.out.persistence.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookSpringDataRepository extends CrudRepository<BookEntity, Long> {
}
