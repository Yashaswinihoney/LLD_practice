public class Main {
    public static void main(String[] args) {
        MarketDataBroker broker = MarketDataBroker.getInstance();

        DashboardWidget aaplChart = new PriceChartWidget("AAPL_UI_CHART");
        DashboardWidget generalTicker = new PriceChartWidget("GLOBAL_TICKER");

        broker.subscribe("AAPL", aaplChart);
        broker.subscribe("AAPL", generalTicker);
        broker.subscribe("TSLA", generalTicker);

        // Simulating a background ingestion thread pushing live data
        Thread ingestionThread = new Thread(() -> {
            broker.publish(new MarketTick("AAPL", 150.25));
            broker.publish(new MarketTick("TSLA", 900.00));
        });

        ingestionThread.start();
    }
}