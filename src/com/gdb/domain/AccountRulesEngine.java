package com.gdb.domain;

public class AccountRulesEngine {

    private static final AccountRulesPropertiesLoader SAVINGS_RULES =
            new AccountRulesPropertiesLoader("config/rules/savings.properties");
    private static final AccountRulesPropertiesLoader CURRENT_RULES =
            new AccountRulesPropertiesLoader("config/rules/current.properties");
    private static final AccountRulesPropertiesLoader FIXED_DEPOSIT_RULES =
            new AccountRulesPropertiesLoader("config/rules/fixeddeposit.properties");
    private static final AccountRulesPropertiesLoader SALARY_RULES =
            new AccountRulesPropertiesLoader("config/rules/salary.properties");

    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) {
            return "privilege";
        }
        if (tenureYears >= 3) {
            return "premium";
        }
        if (tenureYears >= 1) {
            return "standard";
        }
        return "new";
    }

    public static double getSavingsMinBalance(int tenureYears) {
        return SAVINGS_RULES.getDouble("min.balance." + getSavingsBucket(tenureYears), 10000.0);
    }

    public static double getSavingsInterestRate(int tenureYears) {
        return SAVINGS_RULES.getDouble("interest.rate." + getSavingsBucket(tenureYears), 2.70);
    }

    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        double multiplier = CURRENT_RULES.getDouble("overdraft.multiplier", 2.5);
        double minimumLimit = CURRENT_RULES.getDouble("overdraft.min.limit", 25000.0);
        return Math.max(minimumLimit, monthlyTurnover * multiplier);
    }

    public static double getFDInterestRate(int months) {
        if (months >= 36) {
            return FIXED_DEPOSIT_RULES.getDouble("interest.rate.long", 7.50);
        }
        if (months >= 12) {
            return FIXED_DEPOSIT_RULES.getDouble("interest.rate.medium", 6.50);
        }
        return FIXED_DEPOSIT_RULES.getDouble("interest.rate.short", 5.00);
    }

    public static int getSalaryAutoDeactivateMonths() {
        return SALARY_RULES.getInt("auto.deactivate.months", 3);
    }

    public static double getSalaryMinimumMonthlyCredit() {
        return SALARY_RULES.getDouble("minimum.monthly.credit", 10000.0);
    }
}
