// Stateless service class injected with its repository dependency
public class PaymentExecutionService {
    private final TransactionRepository repository;

    public PaymentExecutionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction processPayment(String idempotencyKey, double amount, PaymentStrategy strategy) {
        Transaction newTransaction = new Transaction(idempotencyKey, amount);
        Transaction existing = repository.putIfAbsent(idempotencyKey, newTransaction);

        if (existing != null) {
            throw new DuplicateTransactionException("Transaction " + idempotencyKey + " is already processed/pending.");
        }

        try {
            boolean success = strategy.pay(newTransaction);
            if (success) {
                newTransaction.setStatus(PaymentStatus.SUCCESS);
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
        Transaction tx = repository.getTransaction(transactionId);

        if (tx == null) {
            throw new IllegalArgumentException("Transaction not found for refund.");
        }
        if (tx.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Can only refund SUCCESSFUL transactions.");
        }

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