package com.laba.solvd.customer;

import com.laba.solvd.account.Account;
import com.laba.solvd.account.CurrentAccount;
import com.laba.solvd.account.LoanAccount;
import com.laba.solvd.account.SavingsAccount;
import com.laba.solvd.transaction.Transaction;
import com.laba.solvd.transaction.TransactionProcessable;

import java.time.LocalDate;
import java.util.*;

public class CustomerAccount implements TransactionProcessable {
    private LoanAccount loanAccount;
    private SavingsAccount savingsAccount;
    private CurrentAccount currentAccount;
    private Map<LocalDate, List<Transaction>> transactionsByDate; //Transactions are assigned appropriate dates.
    private static int numberOfAccounts;


    public CustomerAccount(Account account, ArrayList<Transaction> transactions) {
        this.transactionsByDate = new HashMap<>();
        numberOfAccounts++;

        if (account instanceof LoanAccount) {
            this.loanAccount = (LoanAccount) account;
        } else if (account instanceof SavingsAccount) {
            this.savingsAccount = (SavingsAccount) account;
        } else if (account instanceof CurrentAccount) {
            this.currentAccount = (CurrentAccount) account;
        }
        for (Transaction transaction : transactions) {
            addTransaction(transaction.getAmount(), transaction.getDescription(), transaction.getTransactionDate());
        }
    }

    public LoanAccount getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(CurrentAccount currentAccount) {
        this.currentAccount = currentAccount;
    }

    public static int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public static void setNumberOfAccounts(int numberOfAccounts) {
        CustomerAccount.numberOfAccounts = numberOfAccounts;
    }

    public Map<LocalDate, List<Transaction>> getTransactionsByDate() {
        return transactionsByDate;
    }

    public void setTransactionsByDate(Map<LocalDate, List<Transaction>> transactionsByDate) {
        this.transactionsByDate = transactionsByDate;
    }


    @Override
    public void addTransaction(double amount, String description, LocalDate date) {
        Transaction newTransaction = new Transaction(amount, description, date);


        transactionsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(newTransaction);


        if (currentAccount != null) {
            if (amount >= 0) {
                currentAccount.deposit(amount, description);
            } else {
                currentAccount.withdraw(-amount, description);
            }
        }
        if (savingsAccount != null) {
            if (amount >= 0) {
                savingsAccount.deposit(amount, description);
            } else {
                savingsAccount.withdraw(-amount, description);
            }
        }
        if (loanAccount != null) {
            if (amount >= 0) {
                loanAccount.deposit(amount, description);
            } else {
                System.out.println("Withdrawals are not supported for loan accounts.");
            }
        }
    }

    @Override
    public double getTransactionAmount(LocalDate date) {
        List<Transaction> transactionsOnDate = transactionsByDate.get(date);
        if (transactionsOnDate != null) {
            return transactionsOnDate.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
        }
        return 0.0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustomerAccount that = (CustomerAccount) object;
        return Objects.equals(loanAccount, that.loanAccount) && Objects.equals(savingsAccount, that.savingsAccount) && Objects.equals(currentAccount, that.currentAccount) && Objects.equals(transactionsByDate, that.transactionsByDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanAccount, savingsAccount, currentAccount, transactionsByDate);
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "currentAccount=" + currentAccount +
                ", loanAccount=" + loanAccount +
                ", savingsAccount=" + savingsAccount +
                ", transactionsByDate=" + transactionsByDate +
                '}';
    }
}



