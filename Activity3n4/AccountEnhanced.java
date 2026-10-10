public class AccountEnhanced {
    private int accountNumber;
    private String name;
    private int age;
    private String accountType;
    private double balance;
    private String status;
    private Integer pin; 

    public AccountEnhanced(int accountNumber, String name, int age, double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = (age < 18) ? 18 : age;

        if (!accountType.equalsIgnoreCase("Savings") && !accountType.equalsIgnoreCase("Current")) {
            this.accountType = "Savings";
        } else {
            this.accountType = accountType;
        }

        double minBalance = getMinimumBalance();
        this.balance = (initialBalance < minBalance) ? minBalance : initialBalance;

        this.status = "Active";
        this.pin = null;
    }

    private double getMinimumBalance() {
        if (accountType.equalsIgnoreCase("Savings")) {
            return 500;
        } else {
            return 1000;
        }
    }

    public boolean deposit(double amount) {
        if (!status.equalsIgnoreCase("Active")) {
            return false;
        }
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount, int pin) {
        if (!status.equalsIgnoreCase("Active")) {
            return false;
        }
        if (!verifyPin(pin)) {
            return false;
        }
        double minBalance = getMinimumBalance();
        if (balance - amount < minBalance) {
            return false;
        }
        if (amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean closeAccount() {
        if (status.equalsIgnoreCase("Inactive")) {
            return false;
        }
        status = "Inactive";
        return true;
    }

    public boolean reopenAccount() {
        if (status.equalsIgnoreCase("Active")) {
            return false;
        }
        status = "Active";
        return true;
    }

    public boolean setPin(int pin) {
        if (pin >= 1000 && pin <= 9999) {
            this.pin = pin;
            return true;
        }
        return false;
    }


    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }


    public boolean hasPin() {
        return this.pin != null;
    }


    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getAge() {
        return age;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public String getName(){
        return name;
    }
}
