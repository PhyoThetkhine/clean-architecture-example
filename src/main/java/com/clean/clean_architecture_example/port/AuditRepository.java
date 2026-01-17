package com.clean.clean_architecture_example.port;

import com.clean.clean_architecture_example.domain.AuditLog;

public interface AuditRepository {
    AuditLog save(AuditLog audit);
}
