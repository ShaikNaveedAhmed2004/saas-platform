package com.company.saas.dto;

public class LicenseAssignDTO {

    private Long userId;
    private Long appId;

    public LicenseAssignDTO() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}