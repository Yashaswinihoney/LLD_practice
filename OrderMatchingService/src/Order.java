public class Order {
    private final String orderId;
    private final String symbol;
    private final Side side;
    private final double price;
    private final long timestamp;
    private int quantity;

    public Order(String orderId, String symbol, Side side, double price, int quantity) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.side = side;
        this.price = price;
        this.timestamp = System.nanoTime(); //precise
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public Side getSide() {
        return side;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void reduceQuantity(int amount){
        this.quantity-=amount;
    }
}
