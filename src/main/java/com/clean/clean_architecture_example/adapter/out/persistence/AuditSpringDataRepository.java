package com.clean.clean_architecture_example.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

public interface AuditSpringDataRepository extends CrudRepository<AuditEntity, Long> {
}
