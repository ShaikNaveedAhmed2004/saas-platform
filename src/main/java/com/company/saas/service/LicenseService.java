package com.company.saas.service;

import com.company.saas.dto.LicenseAssignDTO;
import com.company.saas.entity.Application;
import com.company.saas.entity.License;
import com.company.saas.entity.User;
import com.company.saas.repository.ApplicationRepository;
import com.company.saas.repository.LicenseRepository;
import com.company.saas.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ApplicationRepository applicationRepository;

    public License assignLicense(LicenseAssignDTO dto){

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getRole().equals("ADMIN"))
            throw new RuntimeException("Admin users do not require licenses");

        Application app = applicationRepository.findById(dto.getAppId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        boolean duplicate =
                licenseRepository.findAll().stream()
                        .anyMatch(l -> l.getUserId().equals(dto.getUserId())
                                && l.getAppId().equals(dto.getAppId())
                                && l.getStatus().equals("ACTIVE"));

        if(duplicate)
            throw new RuntimeException("License already assigned");

        long usedLicenses =
                licenseRepository.findAll().stream()
                        .filter(l -> l.getAppId().equals(dto.getAppId())
                                && l.getStatus().equals("ACTIVE"))
                        .count();

        if(usedLicenses >= app.getLicenseCount())
            throw new RuntimeException("No licenses available");

        auditService.logAction(
                "Assigned application " + dto.getAppId()
                        + " to user " + dto.getUserId());

        License license = new License();

        license.setUserId(dto.getUserId());
        license.setAppId(dto.getAppId());
        license.setStatus("ACTIVE");
        license.setAssignedDate(LocalDate.now());
        license.setLastUsedDate(LocalDate.now());

        return licenseRepository.save(license);
    }

    public void deactivateLicense(Long id){

        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found"));

        auditService.logAction(
                "Deactivated license " + id);
        license.setStatus("INACTIVE");

        licenseRepository.save(license);
    }

    public void updateUsage(Long id){

        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found"));

        if(!license.getStatus().equals("ACTIVE"))
            throw new RuntimeException("Inactive license");

        license.setLastUsedDate(LocalDate.now());
        auditService.logAction(
                "Updated usage for license " + id);

        licenseRepository.save(license);
    }

    public List<License> getAllLicenses(){
        return licenseRepository.findAll();
    }

    public List<License> findUnusedLicenses(){

        LocalDate threshold = LocalDate.now().minusDays(30);

        return licenseRepository.findAll().stream()
                .filter(l -> l.getStatus().equals("ACTIVE"))
                .filter(l -> l.getLastUsedDate().isBefore(threshold))
                .toList();
    }
}