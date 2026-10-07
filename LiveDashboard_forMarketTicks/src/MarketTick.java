public class MarketTick {
    private final String symbol;
    private final double price;
    private final long timestamp;

    public MarketTick(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp=System.nanoTime();
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
