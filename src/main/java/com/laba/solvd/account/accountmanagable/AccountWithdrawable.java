package com.laba.solvd.account.accountmanagable;

public interface AccountWithdrawable {
    void withdraw(double amount);
    void withdraw(double amount, String reason);
}
