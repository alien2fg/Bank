package com.laba.solvd.customer;

import com.laba.solvd.account.Account;
import com.laba.solvd.account.CurrentAccount;
import com.laba.solvd.account.loanaccount.LoanAccount;
import com.laba.solvd.account.savingsaccount.SavingsAccount;
import com.laba.solvd.transaction.Transaction;
import com.laba.solvd.transaction.TransactionProcessable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class CustomerAccount implements TransactionProcessable {
    private static final Logger logger = LogManager.getLogger(CustomerAccount.class);

    private LoanAccount loanAccount;
    private SavingsAccount savingsAccount;
    private CurrentAccount currentAccount;
    private Map<LocalDate, List<Transaction>> transactionsByDate; // Transactions are assigned appropriate dates.
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

        logger.info("Created CustomerAccount with account: {}", account);
    }

    public LoanAccount getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
        logger.debug("Set LoanAccount: {}", loanAccount);
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
        logger.debug("Set SavingsAccount: {}", savingsAccount);
    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(CurrentAccount currentAccount) {
        this.currentAccount = currentAccount;
        logger.debug("Set CurrentAccount: {}", currentAccount);
    }

    public static int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public static void setNumberOfAccounts(int numberOfAccounts) {
        CustomerAccount.numberOfAccounts = numberOfAccounts;
        logger.debug("Set numberOfAccounts: {}", numberOfAccounts);
    }

    public Map<LocalDate, List<Transaction>> getTransactionsByDate() {
        return transactionsByDate;
    }

    public void setTransactionsByDate(Map<LocalDate, List<Transaction>> transactionsByDate) {
        this.transactionsByDate = transactionsByDate;
        logger.debug("Set transactionsByDate: {}", transactionsByDate);
    }

    @Override
    public void addTransaction(double amount, String description, LocalDate date) {
        logger.debug("Adding transaction: amount={}, description={}, date={}", amount, description, date);
        Transaction newTransaction = new Transaction(amount, description, date);
        transactionsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(newTransaction);

        if (currentAccount != null) {
            if (amount >= 0) {
                currentAccount.deposit(amount, description);
                logger.debug("Deposited {} to CurrentAccount", amount);
            } else {
                currentAccount.withdraw(-amount, description);
                logger.debug("Withdrew {} from CurrentAccount", -amount);
            }
        }
        if (savingsAccount != null) {
            if (amount >= 0) {
                savingsAccount.deposit(amount, description);
                logger.debug("Deposited {} to SavingsAccount", amount);
            } else {
                savingsAccount.withdraw(-amount, description);
                logger.debug("Withdrew {} from SavingsAccount", -amount);
            }
        }
        if (loanAccount != null) {
            if (amount >= 0) {
                loanAccount.deposit(amount, description);
                logger.debug("Deposited {} to LoanAccount", amount);
            } else {
                logger.warn("Withdrawals are not supported for LoanAccount");
            }
        }
    }

    @Override
    public double getTransactionAmount(LocalDate date) {
        List<Transaction> transactionsOnDate = transactionsByDate.get(date);
        if (transactionsOnDate != null) {
            double totalAmount = transactionsOnDate.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            logger.debug("Total transaction amount for date {}: {}", date, totalAmount);
            return totalAmount;
        }
        return 0.0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustomerAccount that = (CustomerAccount) object;
        return Objects.equals(loanAccount, that.loanAccount) &&
                Objects.equals(savingsAccount, that.savingsAccount) &&
                Objects.equals(currentAccount, that.currentAccount) &&
                Objects.equals(transactionsByDate, that.transactionsByDate);
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
