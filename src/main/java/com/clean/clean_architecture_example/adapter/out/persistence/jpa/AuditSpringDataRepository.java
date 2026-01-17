package com.clean.clean_architecture_example.adapter.out.persistence.jpa;

import com.clean.clean_architecture_example.adapter.out.persistence.entity.AuditEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuditSpringDataRepository extends CrudRepository<AuditEntity, Long> {
}
