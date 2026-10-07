public class PriceChartWidget implements DashboardWidget{
    private final String widgetId;
    public PriceChartWidget(String widgetId){
        this.widgetId=widgetId;
    }
    @Override
    public void onTickRecieved(MarketTick tick) {
        System.out.println("[" + widgetId + "] Charting " + tick.getSymbol() + " @ $" + tick.getPrice());
    }
}
