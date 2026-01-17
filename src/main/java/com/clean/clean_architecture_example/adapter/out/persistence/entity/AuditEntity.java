package com.clean.clean_architecture_example.adapter.out.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("audit_logs")
public class AuditEntity {

    @Id
    private Long id;
    private String action;
    private String entity;
    private Long entityId;
    private String details;
    private OffsetDateTime createdAt;

    public AuditEntity() {}

    public AuditEntity(Long id, String action, String entity, Long entityId, String details, OffsetDateTime createdAt) {
        this.id = id;
        this.action = action;
        this.entity = entity;
        this.entityId = entityId;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
