package com.company.saas.service;

import com.company.saas.dto.ApplicationDTO;
import com.company.saas.entity.Application;
import com.company.saas.repository.ApplicationRepository;
import com.company.saas.repository.LicenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private AuditService auditService;

    public Application addApplication(ApplicationDTO dto){

        applicationRepository.findAll().forEach(app -> {
            if(app.getAppName().equalsIgnoreCase(dto.getAppName())
                    && app.getVendor().equalsIgnoreCase(dto.getVendor())){
                throw new RuntimeException("Application already exists");
            }
        });

        if(dto.getLicenseCount() <= 0)
            throw new RuntimeException("License count must be > 0");

        if(dto.getCostPerLicense() < 0)
            throw new RuntimeException("Invalid cost");
        auditService.logAction(
                "Added application " + dto.getAppName());

        Application app = new Application();

        app.setAppName(dto.getAppName());
        app.setVendor(dto.getVendor());
        app.setLicenseCount(dto.getLicenseCount());
        app.setCostPerLicense(dto.getCostPerLicense());

        return applicationRepository.save(app);
    }

    public List<Application> getApplications(){
        return applicationRepository.findAll();
    }

    public Application getApplication(Long id){

        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public Application updateApplication(Long id, ApplicationDTO dto){

        Application app = getApplication(id);

        long activeLicenses =
                licenseRepository.findAll().stream()
                        .filter(l -> l.getAppId().equals(id)
                                && l.getStatus().equals("ACTIVE"))
                        .count();

        if(dto.getLicenseCount() < activeLicenses){
            throw new RuntimeException(
                    "Cannot reduce licenses below active assignments");
        }

        app.setLicenseCount(dto.getLicenseCount());
        app.setCostPerLicense(dto.getCostPerLicense());

        return applicationRepository.save(app);
    }

    public void deleteApplication(Long id){

        boolean licenseExists =
                licenseRepository.findAll().stream()
                        .anyMatch(l -> l.getAppId().equals(id)
                                && l.getStatus().equals("ACTIVE"));

        if(licenseExists){
            throw new RuntimeException(
                    "Cannot delete application with active licenses");
        }

        applicationRepository.deleteById(id);
    }
}