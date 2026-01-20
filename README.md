# Expense Management System

A robust, Java-based application for tracking personal finances. This system supports tracking income and expenses, categorizing transactions, viewing real-time balances, and generating reports. It offers both a **Terminal Interface** for efficient keyboard interaction and a **Modern GUI** (Dashboard) for visual management.

## Features

- **Dual Interface**: Run as a CLI tool or a Desktop GUI application.
- **Transaction Management**: Add Income and Expenses with descriptions, categories, and dates.
- **Quick Add**: Sidebar form in GUI for rapid data entry.
- **Deletion**: Remove incorrect transactions via right-click (GUI) or menu option (Terminal).
- **Dashboard**: Visual summary cards for Total Income, Total Expense, and Net Balance.
- **Persistence**: Automatic local data saving (`transactions.dat`) ensures your data is never lost.
- **Reports**: View expenses breakdown by category and chronological transaction history.

## Prerequisites

- **Java JDK 17+**
- **Maven** (for dependency management and building)

## How to Run

### 1. Build the Project
First, compile the source code using Maven:
```bash
mvn clean compile
```

### 2. Run in Terminal Mode (Default)
For the menu-driven command-line interface:
```bash
mvn clean compile exec:java
```

### 3. Run in GUI Mode
For the graphical dashboard interface:
```bash
mvn clean compile exec:java -Dexec.args="gui"
```

## Project Structure

The project follows a clean architecture:
- **`com.expensemanager.model`**: Core data entities (`Transaction`, `TransactionType`).
- **`com.expensemanager.service`**: Business logic (`FinanceService`) and persistence (`StorageService`).
- **`com.expensemanager.gui`**: Swing-based graphical user interface components (`MainFrame`, `Dashboard`, etc.).
- **`com.expensemanager.console`**: Terminal-based user interface (`TerminalUI`).
- **`com.expensemanager.main`**: Application entry point.

## Data Storage

All data is stored locally in a file named `transactions.dat` in the project root. This file is automatically created and updated as you use the application.
