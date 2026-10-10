class Account{
    private int accountNumber;
    private String name;
    private int age;
    private String accountType;
    private double balance;
    private String status;

    public Account(int accountNumber, String name, int age, String accountType,
                   double balance, String status) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }

    public Account(int accountNumber, String name, int age, String accountType,
                   double balance, boolean active) {
        this(accountNumber, name, age, accountType, balance,
                active ? "Active" : "Inactive");
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposited: %.2f%n", amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.printf("Withdrawn: %.2f%n", amount);
        }
    }

    public void display() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Account Type: " + accountType);
        System.out.printf("Balance: %.2f%n", balance);
        System.out.println("Status: " + status);
    }
}

public class TestAccount {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("GLOBAL DIGITAL BANK - ACCOUNT TEST");
        System.out.println("==================================================");

        Account acc1 = new Account(1001, "John Doe", 25, "Savings", 1000.0, "Active");
        System.out.println(">>> 1. Creating Account");
        System.out.println("Account created!");
        acc1.display();

        System.out.println(">>> 2. Deposit Money");
        acc1.deposit(500.0);   // valid
        acc1.deposit(-100.0);  // invalid

        // 3. Withdrawal tests
        System.out.println(">>> 3. Withdraw Money");
        acc1.withdraw(200.0);   // valid
        acc1.withdraw(2000.0);  // insufficient balance

        // 4. Create second account
        System.out.println(">>> 4. Creating Another Account");
        Account acc2 = new Account(1002, "Jane Smith", 30, "Current", 2000.0, true);
        acc2.display();

        // 5. Display all accounts
        System.out.println(">>> 5. All Accounts");
        acc1.display();
        acc2.display();

        System.out.println("==================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("==================================================");
    }
}
