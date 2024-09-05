package com.laba.solvd.account;

import com.laba.solvd.exception.InvalidAmountException;
import com.laba.solvd.util.FinancialUtils;

import java.time.LocalDate;
import java.util.Objects;

public class SavingsAccount extends Account implements InterestCalculable {
    private double interestRate;
    private LocalDate lastInterestAppliedDate;

    public SavingsAccount(String accountNumber, double balance, LocalDate dateOpened, double interestRate) {
        super(accountNumber, balance, dateOpened);
        this.interestRate = interestRate;
        this.lastInterestAppliedDate = dateOpened;
    }


    @Override
    public double calculateInterest() {
        return FinancialUtils.calculateCompoundInterest(getBalance(), interestRate, 1, 1);
    }

    @Override
    public void applyInterest() {
        LocalDate now = LocalDate.now();
        if (now.isAfter(lastInterestAppliedDate.plusYears(1))) {
            double interest = calculateInterest();
            deposit(interest);
            lastInterestAppliedDate = now;
        }
    }

    public final double getFutureValue(int years) {
        return FinancialUtils.predictFutureValue(getBalance(), interestRate, 4, years);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new InvalidAmountException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void deposit(double amount, String description) {
        setBalance(getBalance() + amount);
        System.out.println("Deposit description: " + description);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    @Override
    public void withdraw(double amount, String reason) {
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
            System.out.println("Reason for withdrawal: " + reason);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Interest Rate: " + interestRate + "%";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        SavingsAccount that = (SavingsAccount) object;
        return Double.compare(interestRate, that.interestRate) == 0 && Objects.equals(lastInterestAppliedDate, that.lastInterestAppliedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate, lastInterestAppliedDate);
    }
}