import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class IphoneObservableImpl implements StockObservable{
    private final List<NotificationAlertObserver> observerList= new CopyOnWriteArrayList<>();
    private final AtomicInteger stockCount=new AtomicInteger(0);

    @Override
    public void add(NotificationAlertObserver observer){
        observerList.add(observer);
    }

    @Override
    public void remove(NotificationAlertObserver observer){
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(){
        int currentStock=stockCount.get();
        for (NotificationAlertObserver observer: observerList){
            observer.update(currentStock);
        }
    }

    @Override
    public  void addStock(int newStockAdded){
        if(newStockAdded<=0) return;
        int prevStock=stockCount.getAndAdd(newStockAdded);
        if(prevStock==0){
            notifyObservers();
        }
    }

    @Override
    public int getStockCount() {
        return stockCount.get();
    }
}
