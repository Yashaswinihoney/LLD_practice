public class Main {
    public static void main(String[] args) {
        // 1. Dependency Injection: Initialize components and wire them together manually
        TransactionRepository repository = new TransactionRepository();
        PaymentExecutionService processor = new PaymentExecutionService(repository);

        // 2. Initialize Strategies
        PaymentStrategy cashStrategy = new CashPaymentStrategy(10.00);
        PaymentStrategy cardStrategy = new CardPaymentStrategy("4111222233334444", "123");

        String cashTxId = "TX-CASH-001";
        String cardTxId = "TX-CARD-001";

        System.out.println("--- 1. Initial Payments ---");
        processor.processPayment(cardTxId, 100.00, cardStrategy);
        processor.processPayment(cashTxId, 5.00, cashStrategy);

        System.out.println("\n--- 2. Testing Mismatched Refund Method ---");
        processor.processRefund(cardTxId, cashStrategy);

        System.out.println("\n--- 3. Testing Insufficient Vault Funds ---");
        String bigCashTxId = "TX-CASH-999";
        processor.processPayment(bigCashTxId, 50.00, cashStrategy); // Vault becomes $65

        // Manually bypassing the processor to test the underlying Strategy's lock and math
        Transaction fakeTx = new Transaction("FAKE-1", 100.00);
        cashStrategy.refund(fakeTx);
    }
}