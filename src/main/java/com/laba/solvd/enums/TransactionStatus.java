package com.laba.solvd.enums;

public enum TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED;

    public boolean isFinalized() {
        return this == COMPLETED || this == FAILED;
    }
}
