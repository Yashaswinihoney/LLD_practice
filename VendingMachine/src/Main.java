// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
//        System.out.println("--- SCENARIO 1: SINGLETON VENDING MACHINE ---");
//
//        // 1. Get the single instance of the machine
//        VendingMachine vm = VendingMachine.getInstance();
//
//        try {
//            // User wants a Coke (Price: 25)
//            System.out.println("Attempting to buy Coke (A1)...");
//
//            // Insert 30 cents (Quarter + Nickel)
//            vm.insertCoin(Coin.QUARTER);
//            vm.insertCoin(Coin.NICKEL);
//
//            // Select the product
//            vm.selectProduct("A1");
//            // Output: >>> DISPENSING: Coke <<<
//            // Output: Change returned: 5
//
//            System.out.println("\nAttempting to buy a product without enough money...");
//            vm.insertCoin(Coin.DIME);
//            vm.selectProduct("B2"); // Chips cost 15
//
//        } catch (InsufficientFundsException | IllegalStateException | IllegalArgumentException e) {
//            // This catches the failed Chips purchase safely without crashing the app
//            System.err.println("TRANSACTION FAILED: " + e.getMessage());
//            vm.cancel(); // User cancels and gets their Dime back
//        }


        System.out.println("--- SCENARIO 2: MULTIPLE VENDING MACHINES ---");

        // 1. Instantiate completely independent machines
        VendingMachine lobbyMachine = VendingMachine.getInstance();
        VendingMachine breakroomMachine = VendingMachine.getInstance();

        // 2. User 1 interacts with the Lobby Machine
        System.out.println("\n[Lobby Machine Transaction]");
        lobbyMachine.insertCoin(Coin.QUARTER);
        lobbyMachine.selectProduct("A1"); // Buys Coke

        // 3. User 2 interacts with the Breakroom Machine
        System.out.println("\n[Breakroom Machine Transaction]");
        breakroomMachine.insertCoin(Coin.QUARTER);
        breakroomMachine.insertCoin(Coin.QUARTER);
        breakroomMachine.cancel(); // Refuses to buy, gets 50 cents back

        // 4. Verify Data Isolation
        System.out.println("\n[Data Isolation Check]");
        System.out.println("Lobby Machine Balance: " + lobbyMachine.getBalance());
        System.out.println("Breakroom Machine Balance: " + breakroomMachine.getBalance());

        // Because of the OOP encapsulation, User 1's purchase in the lobby
        // did absolutely nothing to User 2's transaction in the breakroom.
    }
}