package com.clean.clean_architecture_example.adapter.out.persistence;

import com.clean.clean_architecture_example.domain.AuditLog;
import com.clean.clean_architecture_example.port.AuditRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class JdbcAuditRepository implements AuditRepository {

    private final JdbcTemplate jdbc;

    public JdbcAuditRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public AuditLog save(AuditLog audit) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO audit_logs (action, entity, entity_id, details, created_at) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, audit.getAction());
            ps.setString(2, audit.getEntity());
            if (audit.getEntityId() != null) ps.setLong(3, audit.getEntityId()); else ps.setNull(3, java.sql.Types.BIGINT);
            ps.setString(4, audit.getDetails());
            ps.setObject(5, audit.getCreatedAt());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            audit.setId(keyHolder.getKey().longValue());
        }
        return audit;
    }
}
