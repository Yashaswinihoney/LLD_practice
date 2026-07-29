public interface PaymentStrategy {
    boolean pay(Transaction transaction);
    boolean refund(Transaction transaction);
    String getMethodName();
}
