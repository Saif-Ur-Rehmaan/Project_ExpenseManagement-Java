package com.expensemanager.gui;

import com.expensemanager.model.TransactionType;
import com.expensemanager.service.FinanceService;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MainFrame extends JFrame {
    private FinanceService financeService;
    private SummaryPanel summaryPanel;
    private TransactionTablePanel tablePanel;
    private SidebarPanel sidebarPanel;

    public MainFrame() {
        this.financeService = new FinanceService(); // Loads data automatically

        setTitle("Expense Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        refreshData();
    }

    private void initComponents() {
        // Main Content (Left)
        JPanel mainContent = new JPanel(new BorderLayout());

        summaryPanel = new SummaryPanel();
        tablePanel = new TransactionTablePanel(id -> {
            financeService.deleteTransaction(id);
            refreshData();
        });

        mainContent.add(summaryPanel, BorderLayout.NORTH);
        mainContent.add(tablePanel, BorderLayout.CENTER);

        // Sidebar (Right)
        sidebarPanel = new SidebarPanel(data -> {
            financeService.addTransaction(data.amount, LocalDate.now(), data.desc, data.category, data.type);
            refreshData();
        });

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainContent, sidebarPanel);
        splitPane.setResizeWeight(1.0); // Sidebar stays fixed size mostly
        splitPane.setDividerSize(0); // Invisible divider

        add(splitPane);
    }

    private void refreshData() {
        // Update Summary
        summaryPanel.updateValues(
                financeService.getTotalIncome(),
                financeService.getTotalExpense(),
                financeService.getCalculateBalance());

        // Update Table
        tablePanel.updateData(financeService.getAllTransactions());
    }
}
