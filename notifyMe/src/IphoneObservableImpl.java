import java.util.*;

public class IphoneObservableImpl implements StockObservable{
    private final List<NotificationAlertObserver> observerList= new ArrayList<>();
    private int stockCount=0;

    @Override
    public void add(NotificationAlertObserver observer){
        synchronized (observerList){ //object level locking
            observerList.add(observer);
        }
    }

    @Override
    public void remove(NotificationAlertObserver observer){
        synchronized (observerList){
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyObservers(){
        //create a snapshot of observers List to avoid holding the list
        List<NotificationAlertObserver> snapshot;

        synchronized (observerList){
            snapshot=new ArrayList<>(observerList);
        }

        for(NotificationAlertObserver observer: snapshot){
            observer.update();
        }
    }

    @Override
    public  void setStockCount(int newStockAdded){
        synchronized (this){
            if(this.stockCount==0&&newStockAdded>0){
                this.stockCount+=newStockAdded;
                notifyObservers();;
            }
            else{
                this.stockCount+=newStockAdded;
            }
        }
    }
    @Override
    public synchronized int getStockCount(){
        return stockCount;
    }
}
