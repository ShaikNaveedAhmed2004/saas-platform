package com.company.saas.controller;

import com.company.saas.dto.AnalyticsSummaryDTO;
import com.company.saas.service.AnalyticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")

public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public AnalyticsSummaryDTO getSummary() {

        return analyticsService.getSystemSummary();
    }

    @GetMapping("/utilization")
    public double getUtilization() {

        return analyticsService.calculateLicenseUtilization();
    }

    @GetMapping("/cost")
    public double getTotalCost() {

        return analyticsService.calculateTotalCost();
    }

}