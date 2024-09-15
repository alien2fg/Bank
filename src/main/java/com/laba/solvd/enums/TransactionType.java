package com.laba.solvd.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TransactionType {
    DEPOSIT("Deposit to account"),
    WITHDRAWAL("Withdrawal from account"),
    TRANSFER("Transfer between accounts");

    private String description;

    TransactionType(String description){
        this.description =description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<TransactionType> fromString(String type) {
        return Arrays.stream(TransactionType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
