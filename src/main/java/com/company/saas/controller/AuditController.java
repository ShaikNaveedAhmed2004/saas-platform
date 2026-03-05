package com.company.saas.controller;

import com.company.saas.entity.AuditLog;
import com.company.saas.repository.AuditLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")

public class AuditController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getLogs(){

        return auditLogRepository.findAll();
    }
}