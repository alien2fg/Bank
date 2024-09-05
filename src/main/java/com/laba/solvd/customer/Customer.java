package com.laba.solvd.customer;


import com.laba.solvd.customlinkedlist.CustomLinkedList;

import java.util.Objects;

public class Customer implements CustomerInfoProvider{
    private CustomerData customerData;
    private CustomLinkedList<CustomerAccount> accounts;


    public Customer(CustomerData customerData) {
        this.customerData = customerData;
        this.accounts=new CustomLinkedList<>();
    }

    public void addCustomerAccount(CustomerAccount customerAccount) {
       accounts.add(customerAccount);
    }


    public void printCustomerDetails() {
        System.out.println("Customer Details:");
        System.out.println(this);
    }

    public String getFullName() {
        return customerData.getFirstName() + " " + customerData.getLastName();
    }

    public CustomerAddress getCustomerAddress() {
        return customerData.getCustomerAddress();
    }

    public CustomLinkedList<CustomerAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(CustomLinkedList<CustomerAccount> accounts) {
        this.accounts = accounts;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(customerData, customer.customerData) && Objects.equals(accounts, customer.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerData, accounts);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "accounts=" + accounts +
                ", customerData=" + customerData +
                '}';
    }
}
