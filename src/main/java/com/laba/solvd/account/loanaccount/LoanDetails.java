package com.laba.solvd.account.loanaccount;

import com.laba.solvd.exception.InvalidAmountException;

import java.time.LocalDate;

public class LoanDetails {
    private double loanAmount;
    private double interestRate; // Annual interest rate
    private int loanDurationInMonths; // Duration of the loan
    private LocalDate loanStartDate;

    public LoanDetails(double interestRate, double loanAmount, int loanDurationInMonths, LocalDate loanStartDate) {
        if (loanAmount < 0) {
            throw new InvalidAmountException("Loan amount cannot be negative.");
        }
        if (interestRate < 0) {
            throw new InvalidAmountException("Interest rate cannot be negative.");
        }
        if (loanDurationInMonths <= 0) {
            throw new InvalidAmountException("Loan duration must be positive.");
        }
        if (loanStartDate == null) {
            throw new InvalidAmountException("Loan start date cannot be null.");
        }
        this.interestRate = interestRate;
        this.loanAmount = loanAmount;
        this.loanDurationInMonths = loanDurationInMonths;
        this.loanStartDate = loanStartDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanDurationInMonths() {
        return loanDurationInMonths;
    }

    public void setLoanDurationInMonths(int loanDurationInMonths) {
        this.loanDurationInMonths = loanDurationInMonths;
    }

    public LocalDate getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }
}
