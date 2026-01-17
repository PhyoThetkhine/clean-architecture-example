package com.clean.clean_architecture_example.domain;

import java.time.OffsetDateTime;

public class AuditLog {
    private Long id;
    private String action; // e.g. CREATE, UPDATE, DELETE
    private String entity; // e.g. Book
    private Long entityId;
    private String details;
    private OffsetDateTime createdAt;

    public AuditLog() {}

    public AuditLog(String action, String entity, Long entityId, String details) {
        this.action = action;
        this.entity = entity;
        this.entityId = entityId;
        this.details = details;
        this.createdAt = OffsetDateTime.now();
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
