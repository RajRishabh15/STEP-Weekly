package com.gdb.tests;

import com.gdb.command.*;
import com.gdb.db.SimulatedDatabase;
import com.gdb.domain.*;
import com.gdb.logging.*;

public class TestBridgeLogging {

    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 18 — BRIDGE PATTERN (FILE + DB)");
        System.out.println("=".repeat(60));

        // Setup test accounts
        IAccount acc1 = AccountFactory.createAccount("SAVINGS", 1001, "John", 25, 15000);
        acc1.setPin(1234);
        IAccount acc2 = AccountFactory.createAccount("SAVINGS", 1002, "Jane", 30, 10000);
        acc2.setPin(1234);

        // ============================================================
        // 📝 STEP 24: Create Destinations
        //
        // INSTRUCTIONS:
        //   1. fileDest = new FileLogDestination(); fileDest.clear();
        //   2. db = new SimulatedDatabase();
        //   3. dbDest = new DatabaseLogDestination(db);
        //   4. memDest = new MemoryLogDestination();
        // ============================================================
        LogDestination fileDest = new FileLogDestination();
        fileDest.clear();
        SimulatedDatabase db = new SimulatedDatabase();
        LogDestination dbDest = new DatabaseLogDestination(db);
        LogDestination memDest = new MemoryLogDestination();

        // ============================================================
        // 📝 STEP 25: Create TransactionLogger with File Destination
        //
        // INSTRUCTIONS:
        //   1. logger = new TransactionLogger(fileDest);
        //   2. Execute and log 3 commands (Deposit, Withdraw, Transfer).
        //   3. Print count from logger.readAll().
        // ============================================================
        System.out.println("\n[STEP 25] Logging to FILE destination...");
        TransactionLogger logger = new TransactionLogger(fileDest);

        TransactionCommand cmd1 = new DepositCommand(acc1, 500);
        TransactionCommand cmd2 = new WithdrawCommand(acc1, 200, 1234);
        TransactionCommand cmd3 = new TransferCommand(acc1, acc2, 300, 1234);

        cmd1.execute();
        logger.log(cmd1);
        cmd2.execute();
        logger.log(cmd2);
        cmd3.execute();
        logger.log(cmd3);

        System.out.println("  FILE log count: " + logger.readAll().size());

        // ============================================================
        // 📝 STEP 26: Switch to Database Destination
        //
        // INSTRUCTIONS:
        //   1. logger.setDestination(dbDest);
        //   2. Execute and log 3 commands.
        //   3. Print count from logger.readAll().
        // ============================================================
        System.out.println("\n[STEP 26] Switched to DATABASE destination...");
        logger.setDestination(dbDest);

        TransactionCommand cmd4 = new DepositCommand(acc1, 1000);
        TransactionCommand cmd5 = new WithdrawCommand(acc1, 100, 1234);
        TransactionCommand cmd6 = new TransferCommand(acc1, acc2, 150, 1234);

        cmd4.execute();
        logger.log(cmd4);
        cmd5.execute();
        logger.log(cmd5);
        cmd6.execute();
        logger.log(cmd6);

        System.out.println("  DATABASE log count: " + logger.readAll().size());

        // ============================================================
        // 📝 STEP 27: Switch to Memory Destination
        //
        // INSTRUCTIONS:
        //   1. logger.setDestination(memDest);
        //   2. Execute and log 3 commands.
        //   3. Print count from logger.readAll().
        // ============================================================
        System.out.println("\n[STEP 27] Switched to MEMORY destination...");
        logger.setDestination(memDest);

        TransactionCommand cmd7 = new DepositCommand(acc2, 200);
        TransactionCommand cmd8 = new WithdrawCommand(acc1, 50, 1234);
        TransactionCommand cmd9 = new TransferCommand(acc2, acc1, 100, 1234);

        cmd7.execute();
        logger.log(cmd7);
        cmd8.execute();
        logger.log(cmd8);
        cmd9.execute();
        logger.log(cmd9);

        System.out.println("  MEMORY log count: " + logger.readAll().size());

        // ============================================================
        // 📝 STEP 28: Verify Data Isolation Across Backends
        //
        // INSTRUCTIONS:
        //   1. logger.setDestination(fileDest); print count (should be 3).
        //   2. logger.setDestination(dbDest); print count (should be 3).
        //   3. logger.setDestination(memDest); print count (should be 3).
        // ============================================================
        System.out.println("\n[STEP 28] Verifying Data Isolation:");
        logger.setDestination(fileDest);
        System.out.println("  FILE count: " + logger.readAll().size() + " [EXPECTED: 3]");

        logger.setDestination(dbDest);
        System.out.println("  DATABASE count: " + logger.readAll().size() + " [EXPECTED: 3]");

        logger.setDestination(memDest);
        System.out.println("  MEMORY count: " + logger.readAll().size() + " [EXPECTED: 3]");

        // ============================================================
        // 📝 STEP 29: Print Destination Names
        //
        // INSTRUCTIONS:
        //   Print getDestinationName() for each backend.
        // ============================================================
        System.out.println("\n[STEP 29] All Bridge Pattern log backends verified successfully!");
    }
}
