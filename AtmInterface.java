import java.util.Scanner;

class Main {
    private int balance = 0;
    private int lastTransactionAmount = 0;
    private String customerName;
    private String customerId;
    private int loginAttempts = 0;

    public Main(String name, String id) {
        this.customerName = name;
        this.customerId = id;
    }

    private void clearConsole() {
        try {
            System.out.print("\033[H\033[2J"); // ANSI escape code for clearing console
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Unable to clear the console.");
        }
    }

    private void authenticateUser() {
        clearConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, " + customerName + "!");
        System.out.print("Please enter your Customer ID or PIN: ");
        String enteredId = scanner.nextLine();

        if (enteredId.equals(customerId)) {
            clearConsole();
            displayMenu();
        } else {
            System.out.println("Invalid ID or PIN.");
            loginAttempts++;
            if (loginAttempts < 3) {
                authenticateUser();
            } else {
                System.out.println("Too many failed attempts. Exiting system.");
            }
        }
    }

    private void addFunds(int amount) {
        if (amount > 0) {
            balance += amount;
            lastTransactionAmount = amount;
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    private void deductFunds(int amount) {
        if (amount <= balance && amount > 0) {
            balance -= amount;
            lastTransactionAmount = -amount;
            System.out.println("Successfully withdrew $" + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient funds to withdraw $" + amount);
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    private void showLastTransaction() {
        if (lastTransactionAmount > 0) {
            System.out.println("Last Transaction: Deposited $" + lastTransactionAmount);
        } else if (lastTransactionAmount < 0) {
            System.out.println("Last Transaction: Withdrew $" + Math.abs(lastTransactionAmount));
        } else {
            System.out.println("No transactions have been made yet.");
        }
    }

    private void transferFunds(int amount, Main recipient) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            recipient.balance += amount;
            System.out.println("Successfully transferred $" + amount + " to " + recipient.customerName);
        } else if (amount > balance) {
            System.out.println("Transfer failed due to insufficient funds.");
        } else {
            System.out.println("Transfer amount must be positive.");
        }
    }

    private void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        char option;

        do {
            System.out.println("\nMenu:");
            System.out.println("A. Check Balance");
            System.out.println("B. Deposit Funds");
            System.out.println("C. Withdraw Funds");
            System.out.println("D. View Last Transaction");
            System.out.println("E. Transfer Funds");
            System.out.println("F. Exit");
            System.out.print("Choose an option: ");
            option = scanner.next().toUpperCase().charAt(0);

            switch (option) {
                case 'A':
                    clearConsole();
                    System.out.println("Your current balance is: $" + balance);
                    break;

                case 'B':
                    clearConsole();
                    System.out.print("Enter amount to deposit: ");
                    int depositAmount = scanner.nextInt();
                    addFunds(depositAmount);
                    break;

                case 'C':
                    clearConsole();
                    System.out.print("Enter amount to withdraw: ");
                    int withdrawalAmount = scanner.nextInt();
                    deductFunds(withdrawalAmount);
                    break;

                case 'D':
                    clearConsole();
                    showLastTransaction();
                    break;

                case 'E':
                    clearConsole();
                    System.out.print("Enter recipient's name: ");
                    scanner.nextLine(); // Consume newline
                    String recipientName = scanner.nextLine();
                    Main recipient = new Main(recipientName, "TEMP123"); // Temporary ID for example
                    System.out.print("Enter amount to transfer: ");
                    int transferAmount = scanner.nextInt();
                    transferFunds(transferAmount, recipient);
                    break;

                case 'F':
                    clearConsole();
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    clearConsole();
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 'F');
    }

    public static void main(String[] args) {
        Main account = new Main("John Doe", "1234");
        account.authenticateUser();
    }
}
