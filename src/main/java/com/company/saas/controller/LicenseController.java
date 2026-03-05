package com.company.saas.controller;

import com.company.saas.dto.LicenseAssignDTO;
import com.company.saas.entity.License;
import com.company.saas.service.LicenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licenses")

public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @PostMapping("/assign")
    public License assignLicense(@RequestBody LicenseAssignDTO dto) {

        return licenseService.assignLicense(dto);
    }

    @GetMapping
    public List<License> getLicenses() {

        return licenseService.getAllLicenses();
    }

    @PutMapping("/deactivate/{id}")
    public String deactivateLicense(@PathVariable Long id) {

        licenseService.deactivateLicense(id);

        return "License deactivated successfully";
    }

    @PutMapping("/usage/{id}")
    public String updateUsage(@PathVariable Long id) {

        licenseService.updateUsage(id);

        return "License usage updated";
    }

    @GetMapping("/unused")
    public List<License> getUnusedLicenses() {

        return licenseService.findUnusedLicenses();
    }

}