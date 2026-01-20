package com.expensemanager.gui;

import com.expensemanager.model.TransactionType;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.function.Consumer;

public class SidebarPanel extends JPanel {
    private JTextField amountField;
    private JTextField descField;
    private JComboBox<String> catBox;
    private JRadioButton incomeRadio;
    private JRadioButton expenseRadio;
    private JButton addButton;

    // Callback to MainFrame
    private Consumer<TransactionData> onAdd;

    public static class TransactionData {
        public double amount;
        public String desc;
        public String category;
        public TransactionType type;
    }

    public SidebarPanel(Consumer<TransactionData> onAdd) {
        this.onAdd = onAdd;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(45, 48, 50)); // Darker sidebar

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("Quick Add");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +4");
        form.add(title, gbc);

        gbc.gridy++;
        form.add(new JLabel("Type"), gbc);
        gbc.gridy++;
        JPanel radioPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        radioPanel.setOpaque(false);
        incomeRadio = new JRadioButton("Income");
        expenseRadio = new JRadioButton("Expense");
        expenseRadio.setSelected(true); // Default to expense
        ButtonGroup bg = new ButtonGroup();
        bg.add(incomeRadio);
        bg.add(expenseRadio);
        radioPanel.add(incomeRadio);
        radioPanel.add(expenseRadio);
        form.add(radioPanel, gbc);

        gbc.gridy++;
        form.add(new JLabel("Amount"), gbc);
        gbc.gridy++;
        amountField = new JTextField();
        amountField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0.00");
        form.add(amountField, gbc);

        gbc.gridy++;
        form.add(new JLabel("Category"), gbc);
        gbc.gridy++;
        catBox = new JComboBox<>(new String[] { "Food", "Transport", "Rent", "Salary", "Entertainment", "Other" });
        catBox.setEditable(true);
        form.add(catBox, gbc);

        gbc.gridy++;
        form.add(new JLabel("Description"), gbc);
        gbc.gridy++;
        descField = new JTextField();
        form.add(descField, gbc);

        gbc.gridy++;
        addButton = new JButton("Add Transaction");
        addButton.setBackground(new Color(52, 152, 219));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(addButton.getFont().deriveFont(Font.BOLD));
        addButton.addActionListener(e -> handleSubmit());
        form.add(addButton, gbc);

        // Push everything up
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(form, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
    }

    private void handleSubmit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0)
                throw new NumberFormatException();

            TransactionData data = new TransactionData();
            data.amount = amount;
            data.desc = descField.getText();
            data.category = (String) catBox.getSelectedItem();
            data.type = incomeRadio.isSelected() ? TransactionType.INCOME : TransactionType.EXPENSE;

            onAdd.accept(data);

            // Clear
            amountField.setText("");
            descField.setText("");
            amountField.requestFocus();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive amount.", "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
