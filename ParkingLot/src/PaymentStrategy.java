public interface PaymentStrategy {
    boolean process(TransactionRecord transaction);
}
