package com.laba.solvd;

import com.laba.solvd.account.*;
import com.laba.solvd.account.loanaccount.LoanAccount;
import com.laba.solvd.account.loanaccount.LoanDetails;
import com.laba.solvd.account.savingsaccount.SavingsAccount;
import com.laba.solvd.bank.Bank;
import com.laba.solvd.bank.Department;
import com.laba.solvd.customer.Customer;
import com.laba.solvd.customer.CustomerAccount;
import com.laba.solvd.customer.CustomerAddress;
import com.laba.solvd.customer.CustomerData;
import com.laba.solvd.enums.*;
import com.laba.solvd.exception.*;
import com.laba.solvd.transaction.Transaction;
import com.laba.solvd.util.FinancialUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final File customerFile = new File("target/customers.txt");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Bank bank = new Bank("My Bank");
            ArrayList<Customer> customers = new ArrayList<>();

            System.out.println("Welcome to the Bank Management System");
            System.out.println("Enter department location:");
            String departmentLocation = scanner.nextLine();
            System.out.println("Enter department name:");
            String departmentName = scanner.nextLine();
            Department department = new Department(departmentLocation, departmentName);
            System.out.println("Bank: " + bank.getName());
            System.out.println("Department: " + department.getName());
            bank.addDepartment(department);

            boolean running = true;
            while (running) {
                displayMenu();
                int choice = Integer.parseInt(scanner.nextLine());
                try {
                    switch (choice) {
                        case 1:
                            addCustomer(scanner, department, customers);
                            break;
                        case 2:
                            addAccount(scanner, department);
                            break;
                        case 3:
                            displayCustomers(department);
                            break;
                        case 4:
                            printTotalBalance(bank);
                            break;
                        case 5:
                            readDataFromFile(scanner);
                            break;
                        case 6:
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input format. Please enter numeric values where appropriate.");
                }
            }
        }
    }

    private static void readDataFromFile(Scanner scanner) {
        System.out.println("Enter the path of the file to read:");
        if (scanner.hasNextLine()) {
            String filePath = scanner.nextLine();
            File file = new File(filePath);

            if (!file.exists() || !file.isFile()) {
                System.err.println("File does not exist or is not a file.");
                return;
            }

            try {
                String fileContent = FileUtils.readFileToString(file, "UTF-8");
                System.out.println("File Content:");
                System.out.println(fileContent);

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("No input provided for file path.");
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- Bank Menu ---");
        System.out.println("1. Add Customer");
        System.out.println("2. Add Account");
        System.out.println("3. Display Customers");
        System.out.println("4. Display total balance in the bank");
        System.out.println("5. Read data from file");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addCustomer(Scanner scanner, Department department, ArrayList<Customer> customers) {
        try {
            System.out.println("Enter customer first name:");
            String firstName = scanner.nextLine();
            System.out.println("Enter customer last name:");
            String lastName = scanner.nextLine();
            System.out.println("Enter customer date of birth (YYYY-MM-DD):");
            LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
            System.out.println("Enter customer address:");
            System.out.println("Street:");
            String street = scanner.nextLine();
            System.out.println("City:");
            String city = scanner.nextLine();
            CustomerAddress address = new CustomerAddress(street, city);


          CustomerData customerData = new CustomerData(address, dateOfBirth, firstName, lastName);
            Customer customer = new Customer(customerData);
            customers.add(customer);
            department.addCustomer(customer);

            saveCustomerToFile(customer);

            System.out.println("Customer added successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        } catch (IOException e) {
            System.err.println("Error saving customer data: " + e.getMessage());
        }
    }

    private static void saveCustomerToFile(Customer customer) throws IOException {
        String customerData = customer.toString();

        String extractedData = StringUtils.substringBetween(customerData, "CustomerData{", "}}");
        if (extractedData != null) {
            extractedData = extractedData.trim();

            String[] dataParts = StringUtils.split(extractedData, ',');
            String joinedData = StringUtils.join(dataParts, ';');
            String replacedData = StringUtils.replace(joinedData, " ", "_");
            String centeredData = StringUtils.center(replacedData, 120, '*');
            String reversedData = StringUtils.reverse(centeredData);


            FileUtils.writeStringToFile(customerFile,
                    "Extracted Data: " + extractedData + "\n" +
                            "Joined Data: " + joinedData + "\n" +
                            "Replaced Data: " + replacedData + "\n" +
                            "Centered Data: " + centeredData + "\n" +
                            "Reversed Data: " + reversedData + "\n",
                    "UTF-8", true);

            System.out.println("Customer data saved to file.");
        } else {
            System.out.println("CUSTOMERDATA section not found.");
        }
    }

    private static void addAccount(Scanner scanner, Department department) {
        try {
            System.out.println("Select customer by index (0 to " + (department.getCustomers().size() - 1) + "):");
            for (int i = 0; i < department.getCustomers().size(); i++) {
                System.out.println(i + ": " + department.getCustomers().get(i).getFullName());
            }

            int customerIndex = Integer.parseInt(scanner.nextLine());
            if (customerIndex < 0 || customerIndex >= department.getCustomers().size()) {
                throw new InvalidCustomerIndexException("Invalid customer index.");
            }

            Customer customer = department.getCustomers().get(customerIndex);

            System.out.println("Choose account type (1: Current, 2: Savings, 3: Loan):");
            int accountType = Integer.parseInt(scanner.nextLine());

            if (accountType < 1 || accountType > 3) {
                throw new InvalidAccountTypeException("Invalid account type.");
            }

            AccountType selectedAccountType = AccountType.values()[accountType - 1];

            System.out.println("Enter account number:");
            String accountNumber = scanner.nextLine();
            System.out.println("Enter initial balance:");
            double balance = Double.parseDouble(scanner.nextLine());

            Account account = null;
            switch (selectedAccountType) {
                case SAVINGS:
                    System.out.println("Enter interest rate (as a percentage):");
                    double interestRate = Double.parseDouble(scanner.nextLine());
                    account = new SavingsAccount(accountNumber, balance, LocalDate.now(), interestRate);

                    double futureValue = ((SavingsAccount) account).getFutureValue(5); // Example for 5 years
                    System.out.println("Predicted future value after 5 years: " + FinancialUtils.roundToTwoDecimalPlaces(futureValue));
                    break;
                case LOAN:
                    System.out.println("Enter loan amount:");
                    double loanAmount = Double.parseDouble(scanner.nextLine());
                    System.out.println("Enter interest rate (as a percentage):");
                    double loanInterestRate = Double.parseDouble(scanner.nextLine());
                    System.out.println("Enter loan duration in months:");
                    int duration = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter loan start date (YYYY-MM-DD):");
                    LocalDate loanStartDate = LocalDate.parse(scanner.nextLine());
                    LoanDetails loanDetails = new LoanDetails(loanInterestRate, loanAmount, duration, loanStartDate);
                    account = new LoanAccount(accountNumber, balance, LocalDate.now(), loanDetails);

                    double monthlyPayment = ((LoanAccount) account).calculateMonthlyPayment();
                    double totalCost = ((LoanAccount) account).calculateTotalLoanCost();
                    System.out.println("Calculated monthly payment for loan account: " + FinancialUtils.roundToTwoDecimalPlaces(monthlyPayment));
                    System.out.println("Total cost of the loan: " + FinancialUtils.roundToTwoDecimalPlaces(totalCost));
                    break;
                case CURRENT:
                    System.out.println("Enter overdraft limit:");
                    double overdraftLimit = Double.parseDouble(scanner.nextLine());
                    account = new CurrentAccount(accountNumber, balance, LocalDate.now(), overdraftLimit);
                    break;
            }

            System.out.println("Enter transaction description:");
            String description = scanner.nextLine();
            System.out.println("Enter transaction amount:");
            double transactionAmount = Double.parseDouble(scanner.nextLine());
            if (transactionAmount <= 0) {
                throw new InvalidAmountException("Transaction amount must be positive.");
            }
            TransactionStatus status = TransactionStatus.PENDING;
            Transaction transaction = new Transaction(transactionAmount, description, status, LocalDate.now(), TransactionType.DEPOSIT);


            CustomerAccount customerAccount = new CustomerAccount(account, new ArrayList<>(List.of(transaction)), CurrencyType.EUR, CustomerStatus.ACTIVE, selectedAccountType);
            customer.addCustomerAccount(customerAccount);

            System.out.println("Account added successfully.");
        } catch (NumberFormatException | InvalidCustomerIndexException e) {
            System.out.println("Invalid number format. Please enter numeric values where appropriate.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        } catch (InvalidAccountTypeException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayCustomers(Department department) {
        System.out.println("Customer Summary:");
        for (Customer customer : department.getCustomers()) {
            if (customer != null) {
                System.out.println(customer);
            }
        }
    }

    private static void printTotalBalance(Bank bank) {
        double totalBalance = bank.calculateTotalBankBalance();
        System.out.println("Total balance in the bank: " + totalBalance);
    }
}
