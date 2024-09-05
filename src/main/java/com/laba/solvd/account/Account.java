package com.laba.solvd.account;

import com.laba.solvd.account.accountmanagable.AccountManageable;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Account implements AccountManageable {
    protected String accountNumber;
    protected LocalDate dateOpened;
    protected double balance;

    public Account(String accountNumber, double balance, LocalDate dateOpened) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dateOpened = dateOpened;
    }

    public abstract void deposit(double amount);
    public abstract void deposit(double amount, String description);
    public abstract void withdraw(double amount);
    public abstract void withdraw(double amount, String reason);


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", creationDate=" + dateOpened +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Account account = (Account) object;
        return Double.compare(balance, account.balance) == 0 && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(dateOpened, account.dateOpened);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, dateOpened, balance);
    }
}
