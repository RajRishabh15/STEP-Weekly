public class Account {

    // ===== Constants =====
    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    // ===== Fields =====
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    // ===== Constructor =====
    public Account(int accountNumber, String name, int age, double initialBalance, String accountType)
            throws IllegalArgumentException {
        // Validate age (must be >= 18)
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Customer must be at least 18 years old. Provided: " + age);
        }

        // Validate account type (must be "Savings" or "Current")
        if (!"Savings".equalsIgnoreCase(accountType) && !"Current".equalsIgnoreCase(accountType)) {
            throw new IllegalArgumentException("Account type must be 'Savings' or 'Current'. Provided: " + accountType);
        }

        // Validate minimum balance based on account type
        double minBalance = "Savings".equalsIgnoreCase(accountType) ? MIN_BALANCE_SAVINGS : MIN_BALANCE_CURRENT;
        if (initialBalance < minBalance) {
            throw new IllegalArgumentException(accountType + " account requires minimum balance of ₹" + minBalance + ". Provided: ₹" + initialBalance);
        }

        // Initialize all fields
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.status = "Active";
        this.pin = null;
    }

    // ===== Business Methods =====
    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        validateActive();
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Provided: ₹" + amount);
        }
        this.balance += amount;
    }

    public void withdraw(double amount, int pin) throws InvalidAmountException, InsufficientBalanceException,
            MinimumBalanceViolationException, InactiveAccountException, InvalidPinException {
        validateActive();
        if (this.pin == null) {
            throw new InvalidPinException("PIN not set for this account");
        }
        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: ₹" + amount);
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: ₹" + balance + ", Requested: ₹" + amount);
        }
        double minBalance = getMinimumBalance();
        if (balance - amount < minBalance) {
            throw new MinimumBalanceViolationException(
                    "Cannot withdraw. Minimum balance of ₹" + minBalance + " required. Available after withdrawal: ₹"
                            + (balance - amount));
        }
        this.balance -= amount;
    }

    // ===== Account Status Management =====
    public void closeAccount() throws IllegalStateException {
        if ("Inactive".equalsIgnoreCase(status)) {
            throw new IllegalStateException("Account is already closed.");
        }
        this.status = "Inactive";
    }

    public void reopenAccount() throws IllegalStateException {
        if ("Active".equalsIgnoreCase(status)) {
            throw new IllegalStateException("Account is already active.");
        }
        this.status = "Active";
    }

    // ===== PIN Management =====
    public void setPin(int pin) throws IllegalArgumentException {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number.");
        }
        this.pin = pin;
    }

    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    // ===== Helper Methods =====
    private double getMinimumBalance() {
        if ("Savings".equalsIgnoreCase(accountType)) {
            return MIN_BALANCE_SAVINGS;
        } else {
            return MIN_BALANCE_CURRENT;
        }
    }

    private void validateActive() throws InactiveAccountException {
        if (!"Active".equalsIgnoreCase(status)) {
            throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
        }
    }

    // ===== Getters =====
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return "Account #" + accountNumber + " | " + name + " (" + age + " yrs) | " + accountType +
                " | ₹" + balance + " | " + status + " | PIN: " + (hasPin() ? "Yes" : "No");
    }
}
