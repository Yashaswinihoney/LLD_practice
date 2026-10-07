public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== THREAD-SAFE ORDER MATCHING ENGINE ===");
        ExchangeManager exchange = ExchangeManager.getInstance();

        // Simulate concurrent trading flow
        Thread trader1 = new Thread(() -> {
            exchange.submitOrder(new Order("SELL-1", "AAPL", Side.SELL, 150.00, 100));
            exchange.submitOrder(new Order("SELL-2", "AAPL", Side.SELL, 149.50, 50));
        });

        Thread trader2 = new Thread(() -> {
            // This BUY order crosses the spread and should instantly match against SELL-2, then partially fill SELL-1
            exchange.submitOrder(new Order("BUY-1", "AAPL", Side.BUY, 150.25, 120));
        });

        trader1.start();

        // Brief sleep to ensure sell orders hit the book before the aggressive buy order arrives
        Thread.sleep(50);
        trader2.start();

        trader1.join();
        trader2.join();
    }
}