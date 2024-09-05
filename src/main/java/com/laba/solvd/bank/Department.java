package com.laba.solvd.bank;

import com.laba.solvd.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {
    private String name;
    private String departmentlocation;
    private List<Customer> customers;


    public Department(String departmentlocation, String name) {
        this.departmentlocation=departmentlocation;
        this.customers = new ArrayList<>();
        this.name = name;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getDepartmentlocation() {
        return departmentlocation;
    }

    public void setDepartmentlocation(String departmentlocation) {
        this.departmentlocation = departmentlocation;
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
        Department that = (Department) object;
        return Objects.equals(name, that.name) && Objects.equals(departmentlocation, that.departmentlocation) && Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, departmentlocation, customers);
    }

    @Override
    public String toString() {
        return "Department{" +
                "customers=" + customers +
                ", name='" + name + '\'' +
                ", departmentlocation='" + departmentlocation + '\'' +
                '}';
    }
}
