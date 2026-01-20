package com.expensemanager.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    // Unique ID for deletion/editing
    private String id;
    private double amount;
    private LocalDate date;
    private String description;
    private String category;
    private TransactionType type;

    public Transaction(double amount, LocalDate date, String description, String category, TransactionType type) {
        // Validation handled in Service or UI, but good to have safeguard here
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        this.id = java.util.UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %s | $%.2f | %s", id.substring(0, 8), date, type, category, amount,
                description);
    }
}
