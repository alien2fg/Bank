package com.laba.solvd.transaction;

import java.time.LocalDate;

public interface TransactionProcessable {
    void addTransaction(double amount, String description, LocalDate date);  // Method to add a transaction
    double getTransactionAmount(LocalDate date);
}
