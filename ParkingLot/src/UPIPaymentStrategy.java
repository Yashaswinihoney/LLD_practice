public class UPIPaymentStrategy implements PaymentStrategy{
    @Override
    public boolean process(TransactionRecord transaction) {
        System.out.println("UPI pinging UPI Gateway .. Processed for "+transaction.getAmount()+" for ticket "+transaction.getTicketId()+" for transaction "+transaction.getTransactionId());
        return true;
    }
}
