import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ExchangeManager {
    // ConcurrentHashMap allows atomic multi-threaded ticker registration
    private final Map<String, OrderBook> activeBooks = new ConcurrentHashMap<>();

    private ExchangeManager() {}

    private static class InstanceHolder {
        private static final ExchangeManager INSTANCE = new ExchangeManager();
    }

    public static ExchangeManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public OrderBook getBook(String symbol) {
        // computeIfAbsent is an atomic lock-free operation
        return activeBooks.computeIfAbsent(symbol, ticker -> new OrderBook(ticker));
    }

    public void submitOrder(Order order) {
        OrderBook book = getBook(order.getSymbol());
        book.processOrder(order);
    }
}