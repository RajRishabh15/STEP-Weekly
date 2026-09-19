package com.gdb.domain;

import com.gdb.exceptions.*;

public interface IAccount {

    // Getters
    String getAccountNumber();
    String getName();
    int getAge();
    double getBalance();
    String getAccountType();
    String getStatus();

    // Pin utilities
    boolean validatePin(String enteredPin);
    boolean changePin(String oldPin, String newPin);

    // Operations
    void deposit(double amount) throws InvalidAmountException;
    void withdraw(double amount, String enteredPin) throws AccountException;

    // Display
    void displayAccountInfo();
}
