import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class PaymentProcessor {
    private final Map<String, Transaction> transactionRegistry = new ConcurrentHashMap<>();

    public Transaction processPayment(String idempotencyKey, double amount, PaymentStrategy strategy) {
        Transaction newTransaction = new Transaction(idempotencyKey, amount);
        Transaction existing = transactionRegistry.putIfAbsent(idempotencyKey, newTransaction);

        if (existing != null) {
            throw new DuplicateTransactionException("Transaction " + idempotencyKey + " is already processed/pending.");
        }

        try {
            boolean success = strategy.pay(newTransaction);
            if (success) {
                newTransaction.setStatus(PaymentStatus.SUCCESS);
                // NEW: Record the exact strategy used for the payment
                newTransaction.setPaymentMethod(strategy.getMethodName());
                System.out.println("SUCCESS: " + strategy.getMethodName() + " payment completed. TxID: " + idempotencyKey);
            } else {
                newTransaction.setStatus(PaymentStatus.FAILED);
            }
        } catch (Exception e) {
            newTransaction.setStatus(PaymentStatus.FAILED);
            throw new PaymentProcessingException("Gateway error: " + e.getMessage());
        }
        return newTransaction;
    }

    public boolean processRefund(String transactionId, PaymentStrategy strategy) {
        Transaction tx = transactionRegistry.get(transactionId);

        if (tx == null) {
            throw new IllegalArgumentException("Transaction not found for refund.");
        }
        if (tx.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Can only refund SUCCESSFUL transactions.");
        }

        // NEW REQUIREMENT 1: Ensure refund method matches initial payment method
        if (!tx.getPaymentMethod().equals(strategy.getMethodName())) {
            System.err.println("REFUND REJECTED: Cross-method refunds are not permitted. " +
                    "Original: " + tx.getPaymentMethod() + ", Requested: " + strategy.getMethodName());
            return false;
        }

        boolean refundSuccess = strategy.refund(tx);
        if (refundSuccess) {
            tx.setStatus(PaymentStatus.REFUNDED);
            System.out.println("REFUND SUCCESS: TxID " + transactionId);
            return true;
        } else {
            System.err.println("REFUND FAILED at Gateway/Strategy level for TxID: " + transactionId);
            return false;
        }
    }
}