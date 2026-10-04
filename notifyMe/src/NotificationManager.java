import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationManager {
    //high perf threadsafe map to manage multiple product observables
    private final Map<String, StockObservable> inventory=new ConcurrentHashMap<>();
    private  NotificationManager(){}

    private static class InstanceHolder{
        private static final NotificationManager INSTANCE=new NotificationManager();
    }

    public static NotificationManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public void registerProduct(String productId, StockObservable observable){
        inventory.putIfAbsent(productId,observable);
    }

    public void subscribeToProduct(String productId, NotificationAlertObserver observer){
        StockObservable observable=inventory.get(productId);

        if (observable!=null){
            observable.add(observer);
        }
        else{
            System.out.println("ProductId does not match");
        }
    }

    public void receiveStockShipment(String productId, int addedStock) {
        StockObservable observable = inventory.get(productId);
        if (observable != null) {
            // The observable handles its own lock-free AtomicInteger logic safely
            observable.addStock(addedStock);
        }
    }
}
