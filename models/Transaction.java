package models;

import java.time.LocalDate;

/**
 * Abstraction: Abstract class representing a generic financial transaction.
 * Cannot be instantiated directly.
 */
public abstract class Transaction {
    // Encapsulation: Private fields with public getters/setters
    private int id;
    private double amount;
    private String description;
    private LocalDate date;

    public Transaction(int id, double amount, String description, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Abstract method to be implemented by subclasses
    public abstract void display();

    // Getters and Setters (Encapsulation)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
