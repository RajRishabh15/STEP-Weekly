package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestInterfaceFactory {

    public static void main(String[] args) {
        System.out.println("=== Activity 12: Factory-Driven System Suite ===");

        // NOTE: If you completed Activity 11 successfully, paste your working IAccount.java and AccountFactory.java into src/com/gdb.domain (replacing the provided versions).
        // Step 1: Instantiate Savings, Current, and FixedDeposit accounts exclusively through AccountFactory.createAccount()
        // References are strictly of interface type IAccount
        IAccount savings = AccountFactory.createAccount("SAVINGS", "SA101", "Alice", 25, 2000.0, "ACTIVE", "1234");
        IAccount current = AccountFactory.createAccount("CURRENT", "CA102", "Bob", 30, 5000.0, "ACTIVE", "5678");
        IAccount fixedDeposit = AccountFactory.createAccount("FIXED_DEPOSIT", "FD103", "Charlie", 40, 50000.0, "ACTIVE", "9999");

        // [Test 1] Savings Account Creation & Deposit (including minimum balance enforcement)
        try {
            boolean test1Passed = false;
            if (savings != null && savings.getBalance() == 2000.0) {
                savings.deposit(1000.0);
                if (savings.getBalance() == 3000.0) {
                    boolean minBalanceEnforced = false;
                    try {
                        // Min balance is 1000.0. Attempting to withdraw 2500.0 leaves 500.0, violating minimum balance.
                        savings.withdraw(2500.0, "1234");
                    } catch (MinimumBalanceViolationException e) {
                        minBalanceEnforced = true;
                    }
                    if (minBalanceEnforced && savings.getBalance() == 3000.0) {
                        test1Passed = true;
                    }
                }
            }
            if (test1Passed) {
                System.out.println("[Test 1] Savings Account Creation & Deposit: [PASS]");
            } else {
                System.out.println("[Test 1] Savings Account Creation & Deposit: [FAIL]");
            }
        } catch (AccountException e) {
            System.out.println("[Test 1] Savings Account Creation & Deposit: [FAIL] - " + e.getMessage());
        }

        // [Test 2] Current Account Overdraft Withdrawal (including overdraft limit enforcement)
        try {
            boolean test2Passed = false;
            if (current != null && current.getBalance() == 5000.0) {
                // Current account has overdraft limit of 25000.0; withdrawing 15000.0 is permitted
                current.withdraw(15000.0, "5678");
                if (current.getBalance() == -10000.0) {
                    boolean overdraftExceeded = false;
                    try {
                        // Attempting to withdraw 20000.0 more would require 30000 overdraft, exceeding limit
                        current.withdraw(20000.0, "5678");
                    } catch (InsufficientBalanceException e) {
                        overdraftExceeded = true;
                    }
                    if (overdraftExceeded && current.getBalance() == -10000.0) {
                        test2Passed = true;
                    }
                }
            }
            if (test2Passed) {
                System.out.println("[Test 2] Current Account Overdraft Withdrawal: [PASS]");
            } else {
                System.out.println("[Test 2] Current Account Overdraft Withdrawal: [FAIL]");
            }
        } catch (AccountException e) {
            System.out.println("[Test 2] Current Account Overdraft Withdrawal: [FAIL] - " + e.getMessage());
        }

        // [Test 3] Fixed Deposit Premature Withdrawal Block
        try {
            boolean test3Passed = false;
            if (fixedDeposit != null && fixedDeposit.getBalance() == 50000.0) {
                fixedDeposit.deposit(10000.0);
                if (fixedDeposit.getBalance() == 60000.0) {
                    boolean prematureWithdrawalBlocked = false;
                    try {
                        // Fixed Deposit prohibits premature debit
                        fixedDeposit.withdraw(5000.0, "9999");
                    } catch (AccountException e) {
                        prematureWithdrawalBlocked = true;
                    }
                    if (prematureWithdrawalBlocked && fixedDeposit.getBalance() == 60000.0) {
                        test3Passed = true;
                    }
                }
            }
            if (test3Passed) {
                System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]");
            } else {
                System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL]");
            }
        } catch (InvalidAmountException e) {
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL] - " + e.getMessage());
        }

        // [Test 4] Invalid Type Rejection
        try {
            boolean invalidTypeRejected = false;
            try {
                AccountFactory.createAccount("UNKNOWN_TYPE", "INV01", "Dave", 35, 1000.0, "ACTIVE", "0000");
            } catch (IllegalArgumentException e) {
                invalidTypeRejected = true;
            }
            if (invalidTypeRejected) {
                System.out.println("[Test 4] Invalid Type Rejection: [PASS]");
            } else {
                System.out.println("[Test 4] Invalid Type Rejection: [FAIL]");
            }
        } catch (Exception e) {
            System.out.println("[Test 4] Invalid Type Rejection: [FAIL] - " + e.getMessage());
        }

        System.out.println("Factory-driven architecture successfully verified!");
    }
}
