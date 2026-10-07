import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MarketDataBroker {
    private final Map<String, List<DashboardWidget>> topicSubscribers=new ConcurrentHashMap<>();
    private MarketDataBroker(){}
    private static class InstanceHolder{
        private static final MarketDataBroker INSTANCE=new MarketDataBroker();
    }
    public static MarketDataBroker getInstance(){
        return InstanceHolder.INSTANCE;
    }
    public void subscribe(String symbol, DashboardWidget widget){
        topicSubscribers.computeIfAbsent(symbol,l->new CopyOnWriteArrayList<>()).add(widget);
    }
    public void publish(MarketTick tick){
        List<DashboardWidget> widgets=topicSubscribers.get(tick.getSymbol());
        if (widgets!=null){
            for(DashboardWidget widget: widgets){
                widget.onTickRecieved(tick);
            }
        }
    }
}
