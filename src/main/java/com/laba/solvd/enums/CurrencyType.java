package com.laba.solvd.enums;

public enum CurrencyType {
    USD("United States Dollar"),
    EUR("Euro"),
    PLN("Polish Zloty");

    private String currencyName;

    CurrencyType(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
