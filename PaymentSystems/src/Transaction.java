// ==========================================
// 2. TRANSACTION MODEL (Immutable Context)
// ==========================================

class Transaction {
    private final String transactionId;
    private final double amount;
    private PaymentStatus status;
    private String paymentMethod; // NEW: Tracks the original payment method

    public Transaction(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }

    // Package-private to restrict unauthorized state changes
    void setStatus(PaymentStatus status) { this.status = status; }
    void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}