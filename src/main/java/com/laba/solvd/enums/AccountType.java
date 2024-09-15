package com.laba.solvd.enums;

public enum AccountType {
    CURRENT("Current Account", 0.01),
    SAVINGS("Savings Account", 0.05),
    LOAN("Loan Account", -0.02);

    private String description;
    private double interestRate;

    AccountType(String description, double interestRate) {
        this.description = description;
        this.interestRate = interestRate;
    }

    public String getDescription() {
        return description;
    }

    public double getInterestRate() {
        return interestRate;
    }
}