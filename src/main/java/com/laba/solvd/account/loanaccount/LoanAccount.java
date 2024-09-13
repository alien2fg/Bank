package com.laba.solvd.account.loanaccount;

import com.laba.solvd.account.Account;
import com.laba.solvd.exception.InvalidAmountException;
import com.laba.solvd.util.FinancialUtils;

import java.time.LocalDate;
import java.util.Objects;

public class LoanAccount extends Account implements LoanManageable{
    private double loanAmount;
    private double interestRate; // Annual interest rate
    private LocalDate loanStartDate;
    private int loanDurationInMonths; // Duration of the loan

    public LoanAccount(String accountNumber, double balance, LocalDate dateOpened, LoanDetails loanDetails) {
        super(accountNumber, balance, dateOpened);
        this.loanAmount = loanDetails.getLoanAmount();
        this.interestRate = loanDetails.getInterestRate();
        this.loanDurationInMonths = loanDetails.getLoanDurationInMonths();
        this.loanStartDate = loanDetails.getLoanStartDate();
    }

    public final double calculateMonthlyPayment() {
        return FinancialUtils.calculateMonthlyLoanPayment(loanAmount, interestRate, loanDurationInMonths);
    }

    public final double calculateTotalLoanCost() {
        double monthlyPayment = calculateMonthlyPayment();
        return FinancialUtils.calculateTotalLoanCost(monthlyPayment, loanDurationInMonths);
    }

    private void processDeposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Payment amount must be positive.");
        }
        loanAmount -= amount;
        if (loanAmount < 0) {
            loanAmount = 0;
        }
        setBalance(getBalance() + amount);
    }

    @Override
    public void deposit(double amount) {
        processDeposit(amount);
    }

    @Override
    public void deposit(double amount, String description) {
        processDeposit(amount);
        System.out.println("Deposit description: " + description);
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not supported for loan accounts.");
    }

    @Override
    public void withdraw(double amount, String reason) {
        throw new UnsupportedOperationException("Withdrawals are not supported for loan accounts.");
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        if (loanAmount < 0) {
            throw new InvalidAmountException("Loan amount cannot be negative.");
        }
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    public LocalDate getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public int getLoanDurationInMonths() {
        return loanDurationInMonths;
    }

    public void setLoanDurationInMonths(int loanDurationInMonths) {
        if (loanDurationInMonths <= 0) {
            throw new IllegalArgumentException("Loan duration must be positive.");
        }
        this.loanDurationInMonths = loanDurationInMonths;
    }

    @Override
    public String toString() {
        return super.toString() + " | Loan Amount: " + loanAmount + " | Interest Rate: " + interestRate + "% | Duration: " + loanDurationInMonths + " months";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        LoanAccount that = (LoanAccount) object;
        return Double.compare(loanAmount, that.loanAmount) == 0 && Double.compare(interestRate, that.interestRate) == 0 && loanDurationInMonths == that.loanDurationInMonths && Objects.equals(loanStartDate, that.loanStartDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loanAmount, interestRate, loanStartDate, loanDurationInMonths);
    }
}