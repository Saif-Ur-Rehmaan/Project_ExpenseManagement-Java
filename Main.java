
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        System.out.println("====================================");
        System.out.println("   WELCOME TO EXPENSE MANAGER   ");
        System.out.println("====================================");

        while (choice != 4) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. View Transactions & Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Amount: ");
                    double expAmount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Description: ");
                    String expDesc = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String expCat = scanner.nextLine();
                    manager.addTransaction(expAmount, expDesc, expCat);
                    break;
                case 2:
                    System.out.print("Enter Amount: ");
                    double incAmount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Description: ");
                    String incDesc = scanner.nextLine();
                    System.out.print("Enter Source: ");
                    String incSrc = scanner.nextLine();
                    manager.addTransaction(incAmount, incDesc, incSrc, true);
                    break;
                case 3:
                    manager.viewAllTransactions();
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}
