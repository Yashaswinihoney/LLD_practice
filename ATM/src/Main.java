// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Initialize ATM with $500.00 (50000 cents)
        ATMMachine atm = new ATMMachine(50000);

        // Initialize User Account with $200.00 (20000 cents)
        Account aliceAccount = new Account("ACC_123", 1234, 20000);

        System.out.println("--- Scenario 1: Standard Withdrawal ---");
        atm.insertCard(aliceAccount);
        atm.enterPin(1234);
        atm.withdraw(5000); // Withdraw $50.00

        System.out.println("\n--- Scenario 2: Insufficient Bank Funds ---");
        atm.insertCard(aliceAccount);
        atm.enterPin(1234);
        atm.withdraw(30000); // Attempt to withdraw $300.00 (Account only has $150 left)

        System.out.println("\n--- Scenario 3: ATM Out of Cash ---");
        // Initialize a massive account: $10,000.00
        Account bobAccount = new Account("ACC_999", 9999, 1000000);
        atm.insertCard(bobAccount);
        atm.enterPin(9999);
        // Attempt to withdraw $1,000.00 (ATM only has $450 left)
        atm.withdraw(100000);
    }
}