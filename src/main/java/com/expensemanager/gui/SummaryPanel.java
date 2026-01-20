package com.expensemanager.gui;

import javax.swing.*;
import java.awt.*;

public class SummaryPanel extends JPanel {
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;

    public SummaryPanel() {
        setLayout(new GridLayout(1, 3, 20, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        incomeLabel = createCard("Total Income", new Color(46, 204, 113));
        expenseLabel = createCard("Total Expense", new Color(231, 76, 60));
        balanceLabel = createCard("Net Balance", new Color(52, 152, 219));

        add(incomeLabel);
        add(expenseLabel);
        add(balanceLabel);
    }

    private JLabel createCard(String title, Color color) {
        JLabel label = new JLabel("<html><center><span style='font-size:12px; color:#888;'>" + title
                + "</span><br><span style='font-size:24px;'>" + "$0.00" + "</span></center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBackground(new Color(60, 63, 65)); // Slightly lighter than background
        label.setOpaque(true);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, color));
        return label;
    }

    public void updateValues(double income, double expense, double balance) {
        updateCard(incomeLabel, "Total Income", income);
        updateCard(expenseLabel, "Total Expense", expense);
        updateCard(balanceLabel, "Net Balance", balance);
    }

    private void updateCard(JLabel label, String title, double value) {
        label.setText("<html><center><span style='font-size:12px; color:#AAA;'>" + title
                + "</span><br><span style='font-size:24px; font-weight:bold;'>" + String.format("$%.2f", value)
                + "</span></center></html>");
    }
}
