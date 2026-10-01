import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StockMarket {
    private final List<StockObserver> observers=new CopyOnWriteArrayList<>();
    private final Map<String, Double> prices=new ConcurrentHashMap<>();
    public void addObserver(StockObserver obs){
        observers.add(obs);
    }
    public void setPrice(String symbol, Double price){
        prices.put(symbol,price);
        notifyObserver(symbol,price);
    }

    private void notifyObserver(String symbol, Double prices){
        for(var obs: observers){
            obs.update(symbol,prices);
        }
    }
}
