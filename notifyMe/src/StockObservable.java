//Observable (Subject) interface
public interface StockObservable {
    void add(NotificationAlertObserver observer);
    void remove(NotificationAlertObserver observer);
    void notifyObservers();
    void addStock(int newStockAdded);
    int getStockCount();
}
