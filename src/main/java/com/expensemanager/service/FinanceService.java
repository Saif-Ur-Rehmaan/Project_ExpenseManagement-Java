package com.expensemanager.service;

import com.expensemanager.model.Transaction;
import com.expensemanager.model.TransactionType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinanceService {
    private List<Transaction> transactions;
    private StorageService storageService;

    public FinanceService() {
        this.storageService = new StorageService();
        this.transactions = storageService.loadTransactions();

        // Data Migration: Ensure all transactions have an ID
        boolean modified = false;
        for (Transaction t : transactions) {
            if (t.getId() == null) {
                t.setId(java.util.UUID.randomUUID().toString());
                modified = true;
            }
        }
        if (modified) {
            storageService.saveTransactions(transactions);
        }
    }

    public void addTransaction(double amount, LocalDate date, String description, String category,
            TransactionType type) {
        Transaction t = new Transaction(amount, date, description, category, type);
        transactions.add(t);
        storageService.saveTransactions(transactions);
    }

    public boolean deleteTransaction(String id) {
        boolean removed = transactions.removeIf(t -> t.getId() != null && t.getId().equals(id));
        if (removed) {
            storageService.saveTransactions(transactions);
        }
        return removed;
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public double getCalculateBalance() {
        double income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
        double expense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
        return income - expense;
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Map<String, Double> getExpensesByCategory() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));
    }
}
