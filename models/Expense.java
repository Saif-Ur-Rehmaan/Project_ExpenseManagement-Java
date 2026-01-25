package models;

import java.time.LocalDate;

/**
 * Inheritance: Expense inherits from Transaction.
 */
public class Expense extends Transaction {
    private String category;

    public Expense(int id, double amount, String description, LocalDate date, String category) {
        super(id, amount, description, date);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Overriding: Custom display for expenses.
     */
    @Override
    public void display() {
        System.out.printf("[EXPENSE] ID: %d | Amount: -$%.2f | Category: %s | Description: %s | Date: %s%n",
                getId(), getAmount(), category, getDescription(), getDate());
    }
}
