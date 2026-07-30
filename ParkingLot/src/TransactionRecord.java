import java.util.UUID;

public class TransactionRecord {
    private final String transactionId;
    private final double amount;
    private final String ticketId;

    public TransactionRecord(String ticketId, double amount){
        this.amount=amount;
        this.transactionId="TXN-"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.ticketId=ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTicketId() {
        return ticketId;
    }
}
