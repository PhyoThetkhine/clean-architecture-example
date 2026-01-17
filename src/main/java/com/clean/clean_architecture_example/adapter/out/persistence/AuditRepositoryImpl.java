package com.clean.clean_architecture_example.adapter.out.persistence;

import com.clean.clean_architecture_example.adapter.out.persistence.entity.AuditEntity;
import com.clean.clean_architecture_example.adapter.out.persistence.jpa.AuditSpringDataRepository;
import com.clean.clean_architecture_example.domain.AuditLog;
import com.clean.clean_architecture_example.port.AuditRepository;

import java.time.OffsetDateTime;

//@Repository
public class AuditRepositoryImpl implements AuditRepository {

    private final AuditSpringDataRepository repo;

    public AuditRepositoryImpl(AuditSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public AuditLog save(AuditLog audit) {
        OffsetDateTime ts = audit.getCreatedAt() == null ? OffsetDateTime.now() : audit.getCreatedAt();
        AuditEntity e = new AuditEntity(null, audit.getAction(), audit.getEntity(), audit.getEntityId(), audit.getDetails(), ts);
        AuditEntity saved = repo.save(e);
        AuditLog out = new AuditLog(saved.getAction(), saved.getEntity(), saved.getEntityId(), saved.getDetails());
        out.setId(saved.getId());
        out.setCreatedAt(saved.getCreatedAt());
        return out;
    }
}
