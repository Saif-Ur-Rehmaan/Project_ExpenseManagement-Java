package com.expensemanager.gui;

import com.expensemanager.model.Transaction;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class TransactionTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Consumer<String> onDelete;

    public TransactionTablePanel(Consumer<String> onDelete) {
        this.onDelete = onDelete;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JLabel title = new JLabel("Recent Transactions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +2");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Added ID column at index 5 (hidden)
        String[] cols = { "Date", "Type", "Category", "Description", "Amount", "ID" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Hide ID column
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);

        // Context Menu
        JPopupMenu popup = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete Transaction");
        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String id = (String) model.getValueAt(row, 5);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this transaction?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    onDelete.accept(id);
                }
            }
        });
        popup.add(deleteItem);
        table.setComponentPopupMenu(popup);

        // Select row on right click
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);
                    }
                }
            }
        });

        // Custom Renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(60, 63, 65) : new Color(55, 58, 60));
                }

                String type = (String) table.getModel().getValueAt(row, 1);
                if (column == 4) { // Amount
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    if ("INCOME".equals(type))
                        c.setForeground(new Color(46, 204, 113));
                    else if ("EXPENSE".equals(type))
                        c.setForeground(new Color(231, 76, 60));
                } else if (column == 1) { // Type
                    if ("INCOME".equals(type))
                        c.setForeground(new Color(46, 204, 113));
                    else if ("EXPENSE".equals(type))
                        c.setForeground(new Color(231, 76, 60));
                } else {
                    c.setForeground(Color.LIGHT_GRAY);
                }

                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
    }

    public void updateData(List<Transaction> transactions) {
        model.setRowCount(0);
        // Sort by date desc
        transactions.stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                .forEach(t -> model.addRow(new Object[] {
                        t.getDate(),
                        t.getType().toString(),
                        t.getCategory(),
                        t.getDescription(),
                        String.format("$%.2f", t.getAmount()),
                        t.getId()
                }));
    }
}
