package com.company.saas.service;

import com.company.saas.entity.AuditLog;
import com.company.saas.repository.AuditLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(String action){

        AuditLog log = new AuditLog();

        String user = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.setAction(action);
        log.setPerformedBy(user);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}