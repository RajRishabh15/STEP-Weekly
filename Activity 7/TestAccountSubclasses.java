public class TestAccountSubclasses {

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println(" ACCOUNT SUBCLASSES TEST (Savings & Current)");
        System.out.println("============================================================");
        System.out.println();

        // >>> Test 1: Valid SavingsAccount Creation & Interest Calculation
        System.out.println(">>> Test 1: Valid SavingsAccount Creation & Interest Calculation");
        try {
            SavingsAccount savAcc = new SavingsAccount(2001, "Alice Smith", 28, 1500.0);
            savAcc.setPin(1234);
            System.out.println("Created: " + savAcc);
            System.out.println("Interest Rate: " + savAcc.getInterestRate() + "% per annum");
            double interest = savAcc.calculateInterest(3); // 3 years
            System.out.println("Calculated Interest for 3 years on ₹" + savAcc.getBalance() + ": ₹" + interest);
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 2: Invalid SavingsAccount Minimum Balance
        System.out.println(">>> Test 2: Invalid SavingsAccount Minimum Balance (< ₹500)");
        try {
            SavingsAccount savAccInvalid = new SavingsAccount(2002, "Bob Jones", 22, 300.0);
            System.out.println("Created: " + savAccInvalid);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 3: Valid CurrentAccount Creation & Overdraft Usage
        System.out.println(">>> Test 3: Valid CurrentAccount Creation & Overdraft Usage");
        try {
            CurrentAccount currAcc = new CurrentAccount(3001, "TechCorp LLC", 35, 2000.0);
            currAcc.setPin(5678);
            System.out.println("Created: " + currAcc);
            System.out.println("Overdraft Limit: ₹" + currAcc.getOverdraftLimit());
            System.out.println("Available Overdraft: ₹" + currAcc.getAvailableOverdraft());

            // Withdraw beyond balance using overdraft
            System.out.println("Withdrawing ₹3000.0 (using overdraft)...");
            currAcc.withdraw(3000.0, 5678);
            System.out.println("New Balance: ₹" + currAcc.getBalance());
            System.out.println("Overdraft Used: ₹" + currAcc.getOverdraftUsed());
            System.out.println("Available Overdraft: ₹" + currAcc.getAvailableOverdraft());
            System.out.println("Is Using Overdraft? " + currAcc.isUsingOverdraft());

            // Repay Overdraft
            System.out.println("Repaying ₹1000.0 of overdraft...");
            currAcc.repayOverdraft(1000.0);
            System.out.println("New Balance: ₹" + currAcc.getBalance());
            System.out.println("Remaining Overdraft Used: ₹" + currAcc.getOverdraftUsed());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 4: Exceeding Overdraft Limit
        System.out.println(">>> Test 4: Exceeding Overdraft Limit");
        try {
            CurrentAccount currAcc2 = new CurrentAccount(3002, "Startup Inc", 30, 1000.0);
            currAcc2.setPin(9999);
            System.out.println("Created: " + currAcc2);
            System.out.println("Attempting to withdraw ₹10,000.0 (exceeds balance + ₹5000 overdraft)");
            currAcc2.withdraw(10000.0, 9999);
        } catch (AccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println();

        // >>> Test 5: Polymorphism Demo
        System.out.println(">>> Test 5: Polymorphism Demo (Array of Base Account references)");
        try {
            Account[] accounts = new Account[]{
                    new SavingsAccount(2003, "Charlie Brown", 40, 5000.0),
                    new CurrentAccount(3003, "Global Traders", 45, 10000.0)
            };

            for (Account acc : accounts) {
                System.out.println(acc.getAccountType() + " Account -> Min Balance required: ₹" +
                        acc.getMinimumBalance() + " | Details: " + acc);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println();
        System.out.println("============================================================");
        System.out.println(" TEST COMPLETED!");
        System.out.println("============================================================");
    }
}
