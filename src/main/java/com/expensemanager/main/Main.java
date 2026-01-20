package com.expensemanager.main;

import com.expensemanager.console.TerminalUI;
import com.expensemanager.gui.MainFrame;
import com.expensemanager.service.FinanceService;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // If args contains "gui" or no args, verify environment.
        // For dual support, let's look for a flag or env var.
        // Actually prompt says "Terminal-based application... Optional GUI".
        // Let's default to Terminal if running in a TTY, or check args.

        if (args.length > 0 && args[0].equalsIgnoreCase("gui")) {
            launchGUI();
        } else {
            // Default to Terminal as per prompt focus
            System.out.println("Starting Terminal Mode. Run with argument 'gui' for Graphical Interface.");
            new TerminalUI(new FinanceService()).start();
        }
    }

    private static void launchGUI() {
        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
