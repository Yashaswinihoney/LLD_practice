public class CashPaymentStrategy implements PaymentStrategy{
    @Override
    public boolean process(TransactionRecord transaction) {
        System.out.println("CASH processed "+transaction.getAmount()+" for ticket number "+transaction.getTicketId()+" for TXN "+ transaction.getTransactionId());
        return true;
    }
}
