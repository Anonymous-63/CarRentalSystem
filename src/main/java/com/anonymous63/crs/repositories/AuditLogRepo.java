package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {
}
