package com.anonymous63.crs.services;


import com.anonymous63.crs.dtos.AuditLogDto;

public interface AuditLogService {

    void auditLog(AuditLogDto auditLogDTO);
}
