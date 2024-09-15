package com.laba.solvd.enums;

public enum CustomerStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    BANNED("Banned");

    private String status;

    CustomerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}