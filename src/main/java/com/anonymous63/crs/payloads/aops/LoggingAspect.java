package com.anonymous63.crs.payloads.aops;

import com.anonymous63.crs.dtos.AuditLogDto;
import com.anonymous63.crs.services.AuditLogService;
import com.anonymous63.crs.utils.Constants;
import com.anonymous63.crs.utils.CustomeDtoNameResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    private final AuditLogService auditLogService;

    public LoggingAspect(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * Log the save operation of the CrudService
     *
     * @param joinPoint the join point
     */
    @After("execution(* com.anonymous63.crs.services.CrudService.save(..))")
    public void crudServiceSaveLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        String entityName = (args != null && args.length > 0)
                ? CustomeDtoNameResolver.resolve(args[0].getClass())
                : className;

        AuditLogDto auditLog = AuditLogDto.builder()
                .action(Constants.Operation.SAVE.getOperationName())
                .entityName(entityName)
                .methodName(methodName)
                .message(Constants.Operation.SAVE.getOperationName() + " " + entityName)
                .build();

        this.auditLogService.auditLog(auditLog);
    }


}
