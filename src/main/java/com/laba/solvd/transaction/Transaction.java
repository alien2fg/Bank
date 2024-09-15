package com.laba.solvd.transaction;

import com.laba.solvd.enums.TransactionStatus;
import com.laba.solvd.enums.TransactionType;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private LocalDate transactionDate;
    private double amount;
    private String description;
    private TransactionType type;
    private TransactionStatus status;

    public Transaction(double amount, String description, TransactionStatus status,
                       LocalDate transactionDate, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.transactionDate = transactionDate;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Transaction that = (Transaction) object;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(description, that.description) && type == that.type && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionDate, amount, description, type, status);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
