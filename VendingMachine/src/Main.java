public class Main {
    public static void main(String[] args) {
        System.out.println("=== THREAD-SAFE VENDING MACHINE SIMULATION ===");

        // 1. Get the Lock-Free Singleton Manager
        VendingMachineManager manager = VendingMachineManager.getInstance();

        // 2. Instantiate isolated physical machines
        VendingMachine lobbyMachine = manager.getOrCreateMachine("LOBBY_VM");
        VendingMachine breakroomMachine = manager.getOrCreateMachine("BREAKROOM_VM");

        // 3. Administrator Routines (Thread-Safe Restocking)
        System.out.println("\n--- ADMIN: Initializing Machines ---");

        // Setup Lobby Machine (Item cost: 40 cents)
        lobbyMachine.restockProduct("A1", new Product("Coke", 40), 5);
        // Load physical float so the machine can make change
        lobbyMachine.loadPhysicalCoins(Coin.DIME, 5);
        lobbyMachine.loadPhysicalCoins(Coin.NICKEL, 5);

        // Setup Breakroom Machine (Item cost: 15 cents)
        breakroomMachine.restockProduct("B2", new Product("Chips", 15), 10);
        breakroomMachine.loadPhysicalCoins(Coin.QUARTER, 5);

        System.out.println("\n--- USER TRANSACTIONS (CONCURRENT EXECUTIONS) ---");

        // Thread 1: User successfully buys a Coke with change
        Thread user1 = new Thread(() -> {
            System.out.println("\n[User 1] Approaching Lobby Machine...");
            lobbyMachine.insertCoin(Coin.QUARTER); // Balance: 25
            lobbyMachine.insertCoin(Coin.QUARTER); // Balance: 50

            // Price is 40. Change required is 10. 
            // Greedy algorithm will seamlessly dispense 1 DIME.
            lobbyMachine.selectProduct("A1");
        }, "Thread-User-1");

        // Thread 2: User inserts money but cancels
        Thread user2 = new Thread(() -> {
            System.out.println("\n[User 2] Approaching Breakroom Machine...");
            breakroomMachine.insertCoin(Coin.NICKEL); // Balance: 5
            breakroomMachine.insertCoin(Coin.DIME);   // Balance: 15

            // User changes their mind and hits cancel.
            // Greedy algorithm returns exactly 1 DIME and 1 NICKEL.
            breakroomMachine.cancel();
        }, "Thread-User-2");

        // Thread 3: User attempts to buy an item with exact change
        Thread user3 = new Thread(() -> {
            try {
                user1.join();
            } catch (InterruptedException e) {
            } // Wait for readability in console

            System.out.println("\n[User 3] Approaching Lobby Machine for Exact Change...");
            lobbyMachine.insertCoin(Coin.QUARTER);
            lobbyMachine.insertCoin(Coin.DIME);
            lobbyMachine.insertCoin(Coin.NICKEL); // Balance:
        }, "Thread-user-3");

        user1.start();
        user2.start();
        user3.start();
    }
}
