class TestAccountEnhanced{
    public static void main(String[] args) {
        System.out.println("ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
        System.out.println("Test 1: Valid Account Creation");
        AccountEnhanced acc1 = new AccountEnhanced(1001, "John Doe", 25, 1000, "Savings");
        System.out.println("Account #" + acc1.getAccountNumber() + " | " + acc1.getName() +
                " (" + acc1.getAge() + " yrs) | " + acc1.getAccountType() +
                " | ₹" + acc1.getBalance() + " | " + acc1.getStatus() +
                " | PIN: " + (acc1.hasPin() ? "Yes" : "No"));
        
        System.out.println("Test 2: Invalid Age(under 18)");
        AccountEnhanced acc2=new AccountEnhanced(1002, "Young Kid", 16, 400, "Savings");
        System.out.println("Account #" + acc2.getAccountNumber() + " | " + acc2.getName() +
                " (" + acc2.getAge() + " yrs) | " + acc2.getAccountType() +
                " | ₹" + acc2.getBalance() + " | " + acc2.getStatus() +
                " | PIN: " + (acc2.hasPin() ? "Yes" : "No"));

        System.out.println("Test 3:Invalid Account Type");
        AccountEnhanced acc3=new AccountEnhanced(1003, "Test User", 25, 500, "Invalid");
        System.out.println("Account #" + acc3.getAccountNumber() + " | " + acc3.getName() +
                " (" + acc3.getAge() + " yrs) | " + acc3.getAccountType() +
                " | ₹" + acc3.getBalance() + " | " + acc3.getStatus() +
                " | PIN: " + (acc3.hasPin() ? "Yes" : "No"));
        
        System.out.println("Test 4:Minimum Balance Enforcement on Creation");
        AccountEnhanced acc4=new AccountEnhanced(1004, "Bob Wilson", 25, 300, "Savings");
        System.out.println("Account #" + acc4.getAccountNumber() + " | " + acc4.getName() +
                " (" + acc4.getAge() + " yrs) | " + acc4.getAccountType() +
                " | ₹" + acc4.getBalance() + " | " + acc4.getStatus() +
                " | PIN: " + (acc4.hasPin() ? "Yes" : "No"));

        System.out.println("Test 5:Withdrawal with Minimum Balance");
        AccountEnhanced acc5 = new AccountEnhanced(1005, "Alice Brown", 30, 1000, "Current");
        acc5.setPin(1111);
        System.out.println("Initial: Account #" + acc5.getAccountNumber() + " | " + acc5.getName() +
                " (" + acc5.getAge() + " yrs) | " + acc5.getAccountType() +
                " | ₹" + acc5.getBalance() + " | " + acc5.getStatus() +
                " | PIN: " + (acc5.hasPin() ? "Yes" : "No"));
        System.out.println("Withdrawing ₹200.0: " + (acc5.withdraw(200, 1111) ? "SUCCESS" : "FAILED"));
        System.out.println("New balance: ₹" + acc5.getBalance());
        System.out.println("Withdrawing ₹900.0 (would leave ₹-100): " + (acc5.withdraw(900, 1111) ? "SUCCESS" : "FAILED (Minimum balance violation)"));
        System.out.println("Current balance: ₹" + acc5.getBalance());

        System.out.println("\n>>> Test 6: Account Status Management");
        AccountEnhanced acc6 = new AccountEnhanced(1006, "Charlie Green", 35, 2000, "Savings");
        System.out.println("Initial: Account #" + acc6.getAccountNumber() + " | " + acc6.getName() +
                " (" + acc6.getAge() + " yrs) | " + acc6.getAccountType() +
                " | ₹" + acc6.getBalance() + " | " + acc6.getStatus() +
                " | PIN: " + (acc6.hasPin() ? "Yes" : "No"));
        System.out.println("Closing account: " + (acc6.closeAccount() ? "SUCCESS" : "FAILED"));
        System.out.println("After close: Account #" + acc6.getAccountNumber() + " | " + acc6.getName() +
                " | ₹" + acc6.getBalance() + " | " + acc6.getStatus());
        System.out.println("Depositing ₹500.0 to closed account: " + (acc6.deposit(500) ? "SUCCESS" : "FAILED (Account inactive)"));
        System.out.println("Reopening account: " + (acc6.reopenAccount() ? "SUCCESS" : "FAILED"));
        System.out.println("After reopen: Account #" + acc6.getAccountNumber() + " | " + acc6.getName() +
                " | ₹" + acc6.getBalance() + " | " + acc6.getStatus());
                
        System.out.println("Test 7:PIN Protection");
        AccountEnhanced acc7 = new AccountEnhanced(1007, "Diana Prince", 28, 1500, "Savings");
        System.out.println("Setting PIN 1234: " + (acc7.setPin(1234) ? "SUCCESS" : "FAILED"));
        System.out.println("Withdrawing ₹200.0 with correct PIN (1234): " + (acc7.withdraw(200, 1234) ? "SUCCESS" : "FAILED"));
        System.out.println("New balance: ₹" + acc7.getBalance());
        System.out.println("Withdrawing ₹100.0 with incorrect PIN (9999): " + (acc7.withdraw(100, 9999) ? "SUCCESS" : "FAILED (Incorrect PIN)"));
        acc7.closeAccount(); // simulate PIN not set by closing and reopening without PIN
        acc7.reopenAccount();
        AccountEnhanced acc8 = new AccountEnhanced(1008, "NoPin User", 22, 600, "Savings");
        System.out.println("Withdrawing ₹100.0 with PIN not set: " + (acc8.withdraw(100, 1234) ? "SUCCESS" : "FAILED (PIN not set)"));

        // System.out.println("Test 8:All Accounts Summary");
        
    }
}