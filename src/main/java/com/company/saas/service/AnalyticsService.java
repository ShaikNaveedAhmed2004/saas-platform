package com.company.saas.service;

import com.company.saas.dto.AnalyticsSummaryDTO;
import com.company.saas.entity.Application;
import com.company.saas.entity.License;
import com.company.saas.repository.ApplicationRepository;
import com.company.saas.repository.LicenseRepository;
import com.company.saas.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    public AnalyticsSummaryDTO getSystemSummary() {

        AnalyticsSummaryDTO summary = new AnalyticsSummaryDTO();

        long totalUsers = userRepository.count();
        long totalApps = applicationRepository.count();
        long totalLicenses = licenseRepository.count();

        List<License> licenses = licenseRepository.findAll();

        long activeLicenses =
                licenses.stream()
                        .filter(l -> l.getStatus().equals("ACTIVE"))
                        .count();

        LocalDate threshold = LocalDate.now().minusDays(30);

        long unusedLicenses =
                licenses.stream()
                        .filter(l -> l.getStatus().equals("ACTIVE"))
                        .filter(l -> l.getLastUsedDate().isBefore(threshold))
                        .count();

        double totalCost = calculateTotalCost();

        summary.setTotalUsers(totalUsers);
        summary.setTotalApplications(totalApps);
        summary.setTotalLicenses(totalLicenses);
        summary.setActiveLicenses(activeLicenses);
        summary.setUnusedLicenses(unusedLicenses);
        summary.setTotalCost(totalCost);

        return summary;
    }

    public double calculateTotalCost() {

        List<Application> apps = applicationRepository.findAll();

        double totalCost = 0;

        for(Application app : apps){

            double appCost =
                    app.getLicenseCount() * app.getCostPerLicense();

            totalCost += appCost;
        }

        return totalCost;
    }

    public double calculateLicenseUtilization(){

        long totalLicenses = licenseRepository.count();

        long activeLicenses =
                licenseRepository.findAll().stream()
                        .filter(l -> l.getStatus().equals("ACTIVE"))
                        .count();

        if(totalLicenses == 0)
            return 0;

        return ((double) activeLicenses / totalLicenses) * 100;
    }

}