
import models.Transaction;
import models.Expense;
import models.Income;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulation: Manages a private list of transactions.
 */
public class ExpenseManager {
    private List<Transaction> transactions;
    private int nextId = 1;

    public ExpenseManager() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Polymorphism: Adds any object that extends Transaction to the list.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction added successfully!");
    }

    /**
     * Overloading: Method to add an expense with specific parameters.
     */
    public void addTransaction(double amount, String description, String category) {
        Expense expense = new Expense(nextId++, amount, description, LocalDate.now(), category);
        addTransaction(expense);
    }

    /**
     * Overloading: Method to add an income with specific parameters.
     */
    public void addTransaction(double amount, String description, String source, boolean isIncome) {
        if (isIncome) {
            Income income = new Income(nextId++, amount, description, LocalDate.now(), source);
            addTransaction(income);
        }
    }

    /**
     * Polymorphism: Calls the overridden display() method of each object in the
     * list.
     */
    public void viewAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\n--- All Transactions ---");
        for (Transaction t : transactions) {
            t.display(); // Dynamic Method Dispatch (Override usage)
        }
        processBalance();
    }

    private void processBalance() {
        double totalExpense = 0;
        double totalIncome = 0;
        for (Transaction t : transactions) {
            if (t instanceof Expense) {
                totalExpense += t.getAmount();
            } else if (t instanceof Income) {
                totalIncome += t.getAmount();
            }
        }
        System.out.printf("%nTotal Income:  $%.2f%n", totalIncome);
        System.out.printf("Total Expense: $%.2f%n", totalExpense);
        System.out.printf("Net Balance:   $%.2f%n", (totalIncome - totalExpense));
    }
}
