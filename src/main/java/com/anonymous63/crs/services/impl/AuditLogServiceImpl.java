package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.AuditLogDto;
import com.anonymous63.crs.models.AuditLog;
import com.anonymous63.crs.repositories.AuditLogRepo;
import com.anonymous63.crs.services.AuditLogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepo auditLogRepo;
    private final ModelMapper modelMapper;

    public AuditLogServiceImpl(AuditLogRepo auditLogRepo, ModelMapper modelMapper) {
        this.auditLogRepo = auditLogRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public void auditLog(AuditLogDto auditLogDTO) {
        AuditLog auditLog = this.modelMapper.map(auditLogDTO, AuditLog.class);
        this.auditLogRepo.save(auditLog);
    }
}
