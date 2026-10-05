import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Isolates the global state into a dedicated, injectable component (SRP compliance)
public class TransactionRepository {
    private final Map<String, Transaction> transactionRegistry = new ConcurrentHashMap<>();

    public Transaction putIfAbsent(String idempotencyKey, Transaction tx) {
        return transactionRegistry.putIfAbsent(idempotencyKey, tx);
    }

    public Transaction getTransaction(String transactionId) {
        return transactionRegistry.get(transactionId);
    }
}