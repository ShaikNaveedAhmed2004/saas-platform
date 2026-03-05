package com.company.saas.dto;

public class AnalyticsSummaryDTO {

    private long totalUsers;
    private long totalApplications;
    private long totalLicenses;
    private long activeLicenses;
    private long unusedLicenses;
    private double totalCost;

    public AnalyticsSummaryDTO() {}

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getTotalLicenses() {
        return totalLicenses;
    }

    public void setTotalLicenses(long totalLicenses) {
        this.totalLicenses = totalLicenses;
    }

    public long getActiveLicenses() {
        return activeLicenses;
    }

    public void setActiveLicenses(long activeLicenses) {
        this.activeLicenses = activeLicenses;
    }

    public long getUnusedLicenses() {
        return unusedLicenses;
    }

    public void setUnusedLicenses(long unusedLicenses) {
        this.unusedLicenses = unusedLicenses;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}