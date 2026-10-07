public class Main {
    public static void main(String[] args) {
        System.out.println("=== THREAD-SAFE ATM SIMULATION ===");

        // 1. Initialize Singleton Manager & Machine
        ATMManager manager = ATMManager.getInstance();
        manager.registerATM("LOBBY-01", 5000); // ATM has $5000 physical cash
        ATMMachine atm = manager.getATM("LOBBY-01");

        // 2. Create Bank Accounts
        Account aliceAccount = new Account("ACC-100", 1234, 1000); // Alice has $1000

        // 3. Concurrent Simulation (Alice and her husband trying to withdraw simultaneously)
        // Thread 1: Standard ATM Terminal usage
        Thread terminalThread = new Thread(() -> {
            System.out.println("[Terminal Thread] Starting transaction...");
            atm.insertCard(aliceAccount);
            atm.enterPin(1234);
            atm.withdraw(800); // Attempt to withdraw $800
        });

        // Thread 2: Simulating a simultaneous mobile app withdrawal on the same account
        Thread mobileAppThread = new Thread(() -> {
            System.out.println("[Mobile Thread] Attempting digital transfer...");
            aliceAccount.withdraw(500); // Attempt to withdraw $500 directly
        });

        terminalThread.start();
        mobileAppThread.start();
    }
}