
public class TestAccountExceptions {

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println(" ACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("============================================================");
        System.out.println();

        // >>> Test 1: Valid Account Creation
        System.out.println(">>> Test 1: Valid Account Creation");
        Account acc1 = null;
        try {
            acc1 = new Account(1001, "John Doe", 25, 1000.0, "Savings");
            System.out.println("SUCCESS: " + acc1);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 2: Invalid Age (under 18)
        System.out.println(">>> Test 2: Invalid Age (under 18)");
        try {
            Account acc2 = new Account(1002, "Young Kid", 16, 1000.0, "Savings");
            System.out.println("SUCCESS: " + acc2);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 3: Invalid Account Type
        System.out.println(">>> Test 3: Invalid Account Type");
        try {
            Account acc3 = new Account(1003, "Test User", 25, 1000.0, "Invalid");
            System.out.println("SUCCESS: " + acc3);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 4: Minimum Balance on Creation
        System.out.println(">>> Test 4: Minimum Balance on Creation");
        System.out.println("Creating Savings account with ₹300");
        try {
            Account acc4 = new Account(1004, "Bob Wilson", 25, 300.0, "Savings");
            System.out.println("SUCCESS: " + acc4);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 5: Valid Deposit and Withdrawal
        System.out.println(">>> Test 5: Valid Deposit and Withdrawal");
        Account acc5 = null;
        try {
            acc5 = new Account(1005, "Alice Brown", 30, 1000.0, "Current");
            System.out.println("Account: " + acc5);

            acc5.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");

            acc5.deposit(500.0);
            System.out.println("Depositing ₹500.0: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc5.getBalance());

            acc5.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0: SUCCESS");
            System.out.println("Balance after withdrawal: ₹" + acc5.getBalance());
            System.out.println(acc5);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 6: Invalid Deposit (Negative Amount)
        System.out.println(">>> Test 6: Invalid Deposit (Negative Amount)");
        System.out.println("Attempting to deposit ₹-100.0");
        try {
            if (acc5 != null) {
                acc5.deposit(-100.0);
            }
        } catch (InvalidAmountException | InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 7: Insufficient Balance
        System.out.println(">>> Test 7: Insufficient Balance");
        Account acc6 = null;
        try {
            acc6 = new Account(1006, "Charlie Green", 35, 500.0, "Savings");
            acc6.setPin(1234);
            System.out.println("Account: " + acc6);
            System.out.println("Attempting to withdraw ₹1000.0");
            acc6.withdraw(1000.0, 1234);
        } catch (AccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 8: Minimum Balance Violation
        System.out.println(">>> Test 8: Minimum Balance Violation");
        Account acc7 = null;
        try {
            acc7 = new Account(1007, "Diana Prince", 28, 1000.0, "Savings");
            acc7.setPin(1234);
            System.out.println("Account: " + acc7);
            System.out.println("Attempting to withdraw ₹600.0");
            acc7.withdraw(600.0, 1234);
        } catch (AccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 9: Inactive Account Operations
        System.out.println(">>> Test 9: Inactive Account Operations");
        Account acc8 = null;
        try {
            acc8 = new Account(1008, "Eve Wilson", 32, 2000.0, "Current");
            System.out.println("Account: " + acc8);

            acc8.closeAccount();
            System.out.println("Closing account: SUCCESS");

            System.out.println("Attempting to deposit ₹100.0 on closed account");
            try {
                acc8.deposit(100.0);
            } catch (InactiveAccountException e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }

            acc8.reopenAccount();
            System.out.println("Reopening account: SUCCESS");

            acc8.deposit(100.0);
            System.out.println("Depositing ₹100.0 after reopen: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc8.getBalance());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 10: PIN Verification
        System.out.println(">>> Test 10: PIN Verification");
        Account acc9 = null;
        try {
            acc9 = new Account(1009, "Frank Miller", 40, 1500.0, "Savings");
            System.out.println("Account: " + acc9);

            acc9.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");

            acc9.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0 with correct PIN: SUCCESS");
            System.out.println("Balance: ₹" + acc9.getBalance());

            System.out.println("Attempting to withdraw ₹100.0 with incorrect PIN (9999)");
            try {
                acc9.withdraw(100.0, 9999);
            } catch (InvalidPinException e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }

            System.out.println("Attempting to withdraw ₹100.0 without PIN set");
            try {
                if (acc1 != null) {
                    acc1.withdraw(100.0, 1234);
                }
            } catch (InvalidPinException e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 11: All Accounts Summary
        System.out.println(">>> Test 11: All Accounts Summary");
        Account[] accounts = {acc1, acc5, acc6, acc7, acc8, acc9};
        for (Account acc : accounts) {
            if (acc != null) {
                System.out.println(acc);
            }
        }
        System.out.println();
        System.out.println("============================================================");
        System.out.println(" TEST COMPLETED!");
        System.out.println("============================================================");
    }
}
