package com.laba.solvd.transaction;

import com.laba.solvd.enums.TransactionType;

import java.time.LocalDate;

public interface TransactionProcessable {
    void addTransaction(double amount, String description, LocalDate date, TransactionType type);

    double getTransactionAmount(LocalDate date);
}
