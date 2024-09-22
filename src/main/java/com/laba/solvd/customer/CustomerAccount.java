package com.laba.solvd.customer;

import com.laba.solvd.account.Account;
import com.laba.solvd.account.CurrentAccount;
import com.laba.solvd.account.loanaccount.LoanAccount;
import com.laba.solvd.account.savingsaccount.SavingsAccount;
import com.laba.solvd.enums.*;
import com.laba.solvd.transaction.Transaction;
import com.laba.solvd.transaction.TransactionProcessable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;
import java.util.function.*;

public class CustomerAccount implements TransactionProcessable {
    private static final Logger logger = LogManager.getLogger(CustomerAccount.class);

    private LoanAccount loanAccount;
    private SavingsAccount savingsAccount;
    private CurrentAccount currentAccount;
    private Map<LocalDate, List<Transaction>> transactionsByDate;
    private static int numberOfAccounts;
    private CustomerStatus customerStatus;
    private CurrencyType currencyType;
    private AccountType accountType;
    private Map<Transaction, TransactionStatus> transactionStatuses;

    // Lambda expressions
    Predicate<Transaction> isLargeTransaction = transaction -> transaction.getAmount() > 10000;
    Consumer<Transaction> printTransaction = transaction -> logger.info(transaction);
    Supplier<String> generateCustomerId = () -> UUID.randomUUID().toString();
    Function<LocalDate, Double> getTotalTransactionAmount = date ->
            transactionsByDate.getOrDefault(date, new ArrayList<>())
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
    BiFunction<CurrentAccount, Double, Boolean> transferToSavings = (current, amount) -> {
        if (current.getBalance() >= amount) {
            current.withdraw(amount, "Transfer to Savings");
            savingsAccount.deposit(amount, "Transfer from Current");
            return true;
        }
        return false;
    };

    // Constructor
    public CustomerAccount(Account account, ArrayList<Transaction> transactions,
                           CurrencyType currencyType, CustomerStatus status, AccountType accountType) {
        this.transactionsByDate = new HashMap<>();
        this.transactionStatuses = new HashMap<>();
        numberOfAccounts++;
        this.customerStatus = status;
        this.currencyType = currencyType;
        this.accountType = accountType;

        assignAccout(account);

        transactions.stream() // Create a stream from the transactions list
                .forEach(transaction -> addTransaction(
                        transaction.getAmount(),
                        transaction.getDescription(),
                        transaction.getTransactionDate(),
                        TransactionType.DEPOSIT
                ));

        logger.info("Created CustomerAccount with account: {}", account);
    }

    private void assignAccout(Account account) {
        if (account instanceof LoanAccount) {
            this.loanAccount = (LoanAccount) account;
        } else if (account instanceof SavingsAccount) {
            this.savingsAccount = (SavingsAccount) account;
        } else if (account instanceof CurrentAccount) {
            this.currentAccount = (CurrentAccount) account;
        }
    }

    public Optional <LoanAccount> getLoanAccount() {
        return Optional.ofNullable(loanAccount);
    }

    public void setLoanAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
        logger.debug("Set LoanAccount: {}", loanAccount);
    }

    public Optional<SavingsAccount> getSavingsAccount() {
        return Optional.ofNullable(savingsAccount);
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
        logger.debug("Set SavingsAccount: {}", savingsAccount);
    }

    public Optional<CurrentAccount> getCurrentAccount() {
        return Optional.ofNullable(currentAccount);
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

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public void addTransaction(double amount, String description, LocalDate date, TransactionType type) {
        Transaction newTransaction = getNewTransaction(amount, description, date, type);
        addTransactionToMap(date, newTransaction);
        updateTransactionStatus(newTransaction, TransactionStatus.PENDING);
        processTransaction(amount, description, type);
        updateTransactionStatus(newTransaction, TransactionStatus.COMPLETED);
    }

    private void processTransaction(double amount, String description, TransactionType type) {
        switch (type) {
            case DEPOSIT:
                if (currentAccount != null) {
                    currentAccount.deposit(amount, description);
                    logger.debug("Deposited {} to CurrentAccount", amount);
                }
                break;
            case WITHDRAWAL:
                if (currentAccount != null) {
                    currentAccount.withdraw(amount, description);
                    logger.debug("Withdrew {} from CurrentAccount", amount);
                }
                break;
            case TRANSFER:
                logger.info("Transfer transaction is not handled here");
                break;
            default:
                logger.warn("Unknown transaction type");
        }
    }

    private void updateTransactionStatus(Transaction transaction, TransactionStatus status) {
        transactionStatuses.put(transaction, status);
        logger.debug("Transaction status updated to {}: {}", status, transaction);
    }

    private void addTransactionToMap(LocalDate date, Transaction newTransaction) {
        transactionsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(newTransaction);
    }

    private static Transaction getNewTransaction(double amount, String description, LocalDate date, TransactionType type) {
        logger.debug("Adding transaction: amount={}, description={}, date={}, type={}", amount, description, date, type);
        return new Transaction(amount, description, TransactionStatus.PENDING, date, type);
    }

    @Override
    public double getTransactionAmount(LocalDate date) {
        return getTotalTransactionAmount.apply(date);
    }

    public boolean hasLargeTransaction() {
        return transactionsByDate.values().stream()
                .flatMap(List::stream)
                .anyMatch(isLargeTransaction);
    }

    public void printAllTransactions() {
        transactionsByDate.values().stream()
                .flatMap(List::stream)
                .forEach(printTransaction);
    }

    public String createNewCustomerId() {
        return generateCustomerId.get();
    }

    public boolean transferToSavings(double amount) {
        return transferToSavings.apply(currentAccount, amount);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustomerAccount that = (CustomerAccount) object;
        return Objects.equals(loanAccount, that.loanAccount) &&
                Objects.equals(savingsAccount, that.savingsAccount) &&
                Objects.equals(currentAccount, that.currentAccount) &&
                Objects.equals(transactionsByDate, that.transactionsByDate) &&
                Objects.equals(accountType, that.accountType) &&
                Objects.equals(transactionStatuses, that.transactionStatuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanAccount, savingsAccount, currentAccount, transactionsByDate, accountType, transactionStatuses);
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "currentAccount=" + currentAccount +
                ", loanAccount=" + loanAccount +
                ", savingsAccount=" + savingsAccount +
                ", transactionsByDate=" + transactionsByDate +
                ", customerStatus=" + customerStatus +
                ", currencyType=" + currencyType +
                ", accountType=" + accountType +
                ", transactionStatuses=" + transactionStatuses +
                '}';
    }
}
