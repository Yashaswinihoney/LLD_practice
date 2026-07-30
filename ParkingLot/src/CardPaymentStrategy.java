public class CardPaymentStrategy implements PaymentStrategy{
    @Override
    public boolean process(TransactionRecord transaction) {
        System.out.println("CARD Connecting to Bank.. Processed for "+ transaction.getAmount()+" for ticket "+transaction.getTicketId()+" for TXN "+ transaction.getTransactionId());
        return true;
    }
}
