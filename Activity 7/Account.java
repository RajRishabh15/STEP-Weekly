public abstract class Account {

    // ===== Constants =====
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    // ===== Fields =====
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String status;
    private Integer pin;

    // ===== Abstract Methods =====
    public abstract double getMinimumBalance();
    public abstract String getAccountType();

    // ===== Constructor =====
    public Account(int accountNumber, String name, int age, double initialBalance)
            throws IllegalArgumentException {

        // Validate age
        if (age < MIN_AGE) {
            throw new IllegalArgumentException(
                    "Customer must be at least " + MIN_AGE + " years old. Provided: " + age
            );
        }

        // Validate minimum balance (delegated to subclass)
        double minBalance = getMinimumBalance();
        if (initialBalance < minBalance) {
            throw new IllegalArgumentException(
                    getAccountType() + " account requires minimum balance of ₹" + minBalance +
                            ". Provided: ₹" + initialBalance
            );
        }

        // Initialize fields
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
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
        validatePin(pin);
        validateAmount(amount);

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

    // ===== Protected Helper Methods for Subclasses =====
    protected void validateActive() throws InactiveAccountException {
        if (!"Active".equalsIgnoreCase(status)) {
            throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
        }
    }

    protected void validatePin(int pin) throws InvalidPinException {
        if (this.pin == null) {
            throw new InvalidPinException("PIN not set for this account");
        }
        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
    }

    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: ₹" + amount);
        }
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    protected void updateDailyWithdrawalTotal(double amount) {
        // Helper method for withdrawal tracking
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

    public String getStatus() {
        return status;
    }

    public Integer getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return "Account #" + accountNumber + " | " + name + " (" + age + " yrs) | " + getAccountType() +
                " | ₹" + balance + " | " + status + " | PIN: " + (hasPin() ? "Yes" : "No");
    }
}
