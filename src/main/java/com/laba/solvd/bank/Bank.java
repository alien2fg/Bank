package com.laba.solvd.bank;

import com.laba.solvd.customer.Customer;
import com.laba.solvd.customer.CustomerAccount;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bank {
    private static final Logger logger = LogManager.getLogger(Bank.class);
    private static final String DEFAULT_NAME;
    private String name;
    private Set<Department> departments; //avoid duplication of departments

    static {
        DEFAULT_NAME = "My Bank";
        System.out.println("Static block in Bank class executed. Default bank name set to: " + DEFAULT_NAME);
    }

    public Bank() {
        this(DEFAULT_NAME);
    }

    public Bank(String name) {
        this.departments = new HashSet<>();
        this.name = name;
        logger.info("Bank {} has been created", name);
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public double calculateTotalBankBalance() {
        double totalBalance = departments.stream() // Stream over departments
                .flatMap(department -> department.getCustomers().stream()) // Flatten the customers stream
                .flatMap(customer -> customer.getAccounts().stream()) // Flatten the accounts stream
                .mapToDouble(account -> { // Map each account to its balance

                    return account.getCurrentAccount()
                                    .map(currentAccount -> currentAccount.getBalance())
                                    .orElse(0.0)+
                            account.getSavingsAccount()
                                    .map(savingsAccount -> savingsAccount.getBalance())
                                    .orElse(0.0)+
                            account.getLoanAccount()
                                    .map(loanAccount -> loanAccount.getBalance())
                                    .orElse(0.0);

                })
                .sum(); // Sum all balances

        logger.info("Total amount of money in the bank: {}", totalBalance);
        return totalBalance;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Bank bank = (Bank) object;
        return Objects.equals(name, bank.name) && Objects.equals(departments, bank.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, departments);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "departments=" + departments +
                ", name='" + name + '\'' +
                '}';
    }
}
