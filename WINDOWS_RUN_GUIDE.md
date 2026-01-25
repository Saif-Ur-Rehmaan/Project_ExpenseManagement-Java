# Windows Run Guide

This guide explains how to run the Expense Management System on Windows.

## Prerequisites

1.  **Java Installed**: You must have Java Runtime Environment (JRE) or Java Development Kit (JDK) installed.
    - To check, open Command Prompt (`cmd`) and type: `java -version`
    - If it says "command not found", download and install Java (JDK 17 or higher recommended).

## Automatic Run (Recommended)

1.  Locate the file **`expense-manager.exe`** in the `target` folder (or where you placed it).
2.  **Double-click** it.
3.  The application Dashboard (GUI) should open immediately.

_Alternatively, you can use the `run_windows.bat` file if you prefer._

## Troubleshooting

### "The window opens and closes immediately"

This usually means Java is not installed or not in your system PATH.

1.  Open this folder.
2.  Type `cmd` in the address bar and press Enter.
3.  In the black window, type `run_windows.bat` and press Enter.
4.  Read the error message displayed.

### "Unable to access jarfile"

Ensure the file `target/expense-manager-1.0-SNAPSHOT.jar` exists. If not, you may need to build the project first or ensure you copied the `target` folder along with the batch script.

## Manual Run (Advanced)

If you prefer using the Command Prompt (PowerShell or CMD):

1.  Open a terminal in this folder.
2.  Run the following command:
    ```cmd
    java -jar target/expense-manager-1.0-SNAPSHOT.jar gui
    ```
