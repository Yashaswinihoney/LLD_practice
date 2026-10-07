public class Main {
    public static void main(String[] args) {
        System.out.println("=== THREAD-SAFE LIBRARY MANAGEMENT SYSTEM ===");

        LibraryManager library = LibraryManager.getInstance();

        // 1. Initialize Domain
        Book dune = new Book("ISBN-111", "Dune", "Frank Herbert");
        library.getCatalog().addBook(dune);

        BookCopy duneCopy1 = new BookCopy("BC-001", dune);
        library.addInventory(duneCopy1);

        Member alice = new Member("M-Alice");
        Member bob = new Member("M-Bob");
        Member charlie = new Member("M-Charlie");

        library.registerMember(alice);
        library.registerMember(bob);
        library.registerMember(charlie);

        // 2. Simulate Fine Block
        alice.addFine(60.00); // Exceeds $50 limit
        library.checkoutBook(alice.getMemberId(), "BC-001"); // Should Block

        // 3. Simulate Concurrent Checkout Race Condition
        System.out.println("\n--- Concurrent Race for 1 Copy ---");
        Thread t1 = new Thread(() -> library.checkoutBook(bob.getMemberId(), "BC-001"));
        Thread t2 = new Thread(() -> library.checkoutBook(charlie.getMemberId(), "BC-001"));

        t1.start(); t2.start();

        try { t1.join(); t2.join(); } catch (InterruptedException e) {}

        // 4. Simulate Waitlist and Returns
        System.out.println("\n--- Waitlist & Return Lifecycle ---");
        dune.addToWaitlist("M-Alice");

        // Bob returns the book 5 days late (Penalty: $12.50)
        // This will instantly trigger Alice's waitlist notification
        library.returnBook(bob.getMemberId(), "BC-001", 5);
    }
}