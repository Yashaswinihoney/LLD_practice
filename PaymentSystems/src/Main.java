import java.util.UUID;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        // Initialize Cash machine with only $10 in the vault
        PaymentStrategy cashStrategy = new CashPaymentStrategy(10.00);
        PaymentStrategy cardStrategy = new CardPaymentStrategy("4111222233334444", "123");

        String cashTxId = "TX-CASH-001";
        String cardTxId = "TX-CARD-001";

        System.out.println("--- 1. Initial Payments ---");
        processor.processPayment(cardTxId, 100.00, cardStrategy);

        // User pays $5 in cash. Vault balance goes from $10 -> $15
        processor.processPayment(cashTxId, 5.00, cashStrategy);

        System.out.println("\n--- 2. Testing Mismatched Refund Method ---");
        // Attempting to refund a Card transaction using Cash
        processor.processRefund(cardTxId, cashStrategy);

        System.out.println("\n--- 3. Testing Insufficient Vault Funds ---");
        // Let's fake a massive cash transaction just to test the refund limit
        String bigCashTxId = "TX-CASH-999";
        processor.processPayment(bigCashTxId, 50.00, cashStrategy); // Vault is now $65

        // We manually try to refund more than the vault has by overriding the strategy
        // Let's pretend a user wants a $100 refund, but the vault only has $65.
        Transaction fakeTx = new Transaction("FAKE-1", 100.00);
        cashStrategy.refund(fakeTx);
    }
}