package com.laba.solvd.account;

import com.laba.solvd.exception.InvalidAmountException;

import java.time.LocalDate;
import java.util.Objects;

public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, double balance, LocalDate dateOpened, double overdraftLimit) {
        super(accountNumber, balance, dateOpened);
        this.overdraftLimit = overdraftLimit;
    }


    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        if (overdraftLimit < 0) {
            throw new InvalidAmountException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        setBalance(getBalance() + amount);
    }

    @Override
    public void deposit(double amount, String description) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        setBalance(getBalance() + amount);
        System.out.println("Deposit description: " + description);
    }


    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        double newBalance = getBalance() - amount;
        if (newBalance < -overdraftLimit) {
            System.out.println("Insufficient funds and overdraft limit exceeded.");
        } else {
            setBalance(newBalance);
        }
    }

    @Override
    public void withdraw(double amount, String reason) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        double newBalance = getBalance() - amount;
        if (newBalance < -overdraftLimit) {
            System.out.println("Insufficient funds and overdraft limit exceeded.");
        } else {
            setBalance(newBalance);
            System.out.println("Reason for withdrawal: " + reason);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Overdraft Limit: " + overdraftLimit;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        CurrentAccount that = (CurrentAccount) object;
        return Double.compare(overdraftLimit, that.overdraftLimit) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overdraftLimit);
    }
}