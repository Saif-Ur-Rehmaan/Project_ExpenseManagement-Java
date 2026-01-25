package models;

import java.time.LocalDate;

/**
 * Inheritance: Income inherits from Transaction.
 */
public class Income extends Transaction {
    private String source;

    public Income(int id, double amount, String description, LocalDate date, String source) {
        super(id, amount, description, date);
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Overriding: Custom display for income.
     */
    @Override
    public void display() {
        System.out.printf("[INCOME]  ID: %d | Amount: +$%.2f | Source: %s   | Description: %s | Date: %s%n",
                getId(), getAmount(), source, getDescription(), getDate());
    }
}
