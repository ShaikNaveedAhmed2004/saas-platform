package com.company.saas.dto;

import jakarta.validation.constraints.NotBlank;

public class ApplicationDTO {

    @NotBlank
    private String appName;

    private String vendor;

    private int licenseCount;

    private double costPerLicense;

    public ApplicationDTO() {}

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getLicenseCount() {
        return licenseCount;
    }

    public void setLicenseCount(int licenseCount) {
        this.licenseCount = licenseCount;
    }

    public double getCostPerLicense() {
        return costPerLicense;
    }

    public void setCostPerLicense(double costPerLicense) {
        this.costPerLicense = costPerLicense;
    }
}