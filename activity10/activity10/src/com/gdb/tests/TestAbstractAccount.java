package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAbstractAccount {

    /**
     * Transfers funds from source account to destination account using PIN
     * authentication. If withdrawal fails (wrong PIN, inactive account,
     * insufficient funds), the destination account is NOT credited.
     *
     * @param from source account to withdraw from
     * @param to destination account to deposit into
     * @param amount amount to transfer
     * @param pin PIN of the source account
     * @return true if transfer succeeded, false otherwise
     */
    public static boolean transferFunds(AbstractAccount from, AbstractAccount to, double amount, String pin) {
        try {
            // Step 2: Attempt to withdraw from source account using provided PIN
            from.withdraw(amount, pin);
            // If withdrawal succeeds, deposit the amount into the destination account
            to.deposit(amount);
            return true;
        } catch (AccountException e) {
            // If withdrawal throws an exception (wrong PIN, inactive, insufficient funds), destination is NOT credited
            System.out.println("Transfer failed: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Activity 10: Banking Operations Suite ===");

        // NOTE: If you completed Activity 9 successfully, paste your working domain classes into src/com/gdb/domain (replacing the provided versions).
        // TODO: Step 1 - Create an array/portfolio of AbstractAccount objects (SavingsAccount, CurrentAccount, SalaryAccount)
        // SavingsAccount starts with Rs 10000, PIN = "1234"
        // CurrentAccount starts with Rs 5000, overdraft Rs 2000, PIN = "5678"
        // SalaryAccount starts with Rs 20000, employer = "TechCorp", PIN = "9999"
        AbstractAccount[] portfolio = new AbstractAccount[]{
            new SavingsAccount("SAV001", "Alice", 30, 10000.0, "ACTIVE", "1234"),
            new CurrentAccount("CUR001", "Bob", 25, 5000.0, "ACTIVE", "5678", 2000.0),
            new SalaryAccount("SAL001", "Charlie", 28, 20000.0, "ACTIVE", "9999", "TechCorp")
        };

        // TODO: Step 2 - Implement and test secure fund transfer from Savings to Current account with PIN authentication
        // Transfer Rs 3000 from Savings (index 0) to Current (index 1) using correct PIN "1234"
        boolean transferResult = transferFunds(portfolio[0], portfolio[1], 3000.0, "1234");
        if (transferResult) {
            System.out.println("Transfer Rs 3000 from Savings to Current: SUCCESS");
            System.out.println("Savings Balance: Rs " + portfolio[0].getBalance()
                    + " | Current Balance: Rs " + portfolio[1].getBalance());
        } else {
            System.out.println("Transfer Rs 3000 from Savings to Current: FAILED");
        }

        // TODO: Step 3 - Test failed transfer with wrong PIN and verify no balance was credited/debited
        // Record balances before attempted failed transfer
        double savingsBalanceBefore = portfolio[0].getBalance();
        double currentBalanceBefore = portfolio[1].getBalance();

        // Attempt transfer with wrong PIN - should fail and leave balances unchanged
        boolean failedTransfer = transferFunds(portfolio[0], portfolio[1], 1000.0, "0000");
        if (!failedTransfer
                && portfolio[0].getBalance() == savingsBalanceBefore
                && portfolio[1].getBalance() == currentBalanceBefore) {
            System.out.println("Failed Transfer (Wrong PIN): Exception caught, no balance changed [PASS]");
        } else {
            System.out.println("Failed Transfer (Wrong PIN): Test FAILED - balances were incorrectly modified");
        }

        // TODO: Step 4 - Process monthly cycle applying interest to every SavingsAccount and checking each SalaryAccount's inactive months
        for (AbstractAccount account : portfolio) {
            switch (account) {
                case SavingsAccount savingsAccount -> // Apply monthly interest to SavingsAccount
                    savingsAccount.applyInterest();
                case SalaryAccount salaryAcc -> {
                    // Check inactive months for SalaryAccount
                    salaryAcc.incrementInactiveMonths();
                    if (salaryAcc.getInactiveMonths() >= 3) {
                        System.out.println("Warning: Salary account " + salaryAcc.getAccountNumber()
                                + " has been inactive for " + salaryAcc.getInactiveMonths() + " month(s).");
                    }
                }
                default -> {
                }
            }
        }
        System.out.println("Monthly Interest Cycle processed for all qualifying accounts.");

        System.out.println("All banking operations passed!");
    }
}
