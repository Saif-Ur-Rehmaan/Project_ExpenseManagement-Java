package com.expensemanager.console;

import com.expensemanager.model.Transaction;
import com.expensemanager.model.TransactionType;
import com.expensemanager.service.FinanceService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class TerminalUI {
    private FinanceService financeService;
    private Scanner scanner;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public TerminalUI(FinanceService financeService) {
        this.financeService = financeService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    addTransaction(TransactionType.INCOME);
                    break;
                case "2":
                    addTransaction(TransactionType.EXPENSE);
                    break;
                case "3":
                    viewBalance();
                    break;
                case "4":
                    viewReports();
                    break;
                case "5":
                    deleteTransaction();
                    break;
                case "6":
                    System.out.println("Exiting... Data saved.");
                    return;
                default:
                    System.out.println(ANSI_RED + "Invalid option." + ANSI_RESET);
            }
        }
    }

    private void printMenu() {
        System.out.println(ANSI_CYAN + "\n--- Expense Management System ---" + ANSI_RESET);
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Balance");
        System.out.println("4. View Reports");
        System.out.println("5. Delete Transaction");
        System.out.println("6. Exit");
        System.out.print("Select Option: ");
    }

    private void addTransaction(TransactionType type) {
        try {
            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Description: ");
            String desc = scanner.nextLine();

            System.out.print("Enter Category: ");
            String cat = scanner.nextLine();

            // Default to today, or could ask for date
            financeService.addTransaction(amount, LocalDate.now(), desc, cat, type);
            System.out.println(ANSI_GREEN + type + " added successfully." + ANSI_RESET);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Invalid amount format." + ANSI_RESET);
        }
    }

    private void deleteTransaction() {
        System.out.println("\n--- Delete Transaction ---");
        var transactions = financeService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions to delete.");
            return;
        }

        // List with indices
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }

        System.out.print("Enter number to delete (0 to cancel): ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index > 0 && index <= transactions.size()) {
                Transaction t = transactions.get(index - 1);
                if (financeService.deleteTransaction(t.getId())) {
                    System.out.println(ANSI_GREEN + "Transaction deleted." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Failed to delete." + ANSI_RESET);
                }
            } else if (index != 0) {
                System.out.println(ANSI_RED + "Invalid selection." + ANSI_RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Invalid input." + ANSI_RESET);
        }
    }

    private void viewBalance() {
        double income = financeService.getTotalIncome();
        double expense = financeService.getTotalExpense();
        double balance = financeService.getCalculateBalance();

        System.out.println("\n--- Balance Summary ---");
        System.out.println("Total Income:  " + ANSI_GREEN + "$" + String.format("%.2f", income) + ANSI_RESET);
        System.out.println("Total Expense: " + ANSI_RED + "$" + String.format("%.2f", expense) + ANSI_RESET);
        System.out.println("Net Balance:   " + ANSI_CYAN + "$" + String.format("%.2f", balance) + ANSI_RESET);
    }

    private void viewReports() {
        System.out.println("\n--- Expense by Category ---");
        Map<String, Double> expenses = financeService.getExpensesByCategory();
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            expenses.forEach((k, v) -> System.out.println(k + ": $" + String.format("%.2f", v)));
        }

        System.out.println("\n--- Recent Transactions ---");
        financeService.getAllTransactions().stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                .limit(5)
                .forEach(System.out::println);
    }
}
