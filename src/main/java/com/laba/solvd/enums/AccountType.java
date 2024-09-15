package com.laba.solvd.enums;

public enum AccountType {
    CURRENT("Current Account"),
    SAVINGS("Savings Account"),
    LOAN("Loan Account");

    private String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}