package com.laba.solvd.util;

public final class FinancialUtils {

    private FinancialUtils() {

    }

    public static double calculateCompoundInterest(double principal, double annualInterestRate, int timesCompounded, int years) {
        double rate = annualInterestRate / 100;
        return principal * Math.pow(1 + rate / timesCompounded, timesCompounded * years);
    }

    public static double predictFutureValue(double principal, double annualInterestRate, int timesCompounded, int years) {
        return calculateCompoundInterest(principal, annualInterestRate, timesCompounded, years);
    }


    public static double calculateMonthlyLoanPayment(double loanAmount, double annualInterestRate, int numberOfPayments) {
        double monthlyRate = annualInterestRate / 100 / 12;
        return loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, numberOfPayments)) / (Math.pow(1 + monthlyRate, numberOfPayments) - 1);
    }


    public static double calculateTotalLoanCost(double monthlyPayment, int numberOfPayments) {
        return monthlyPayment * numberOfPayments;
    }


    public static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
