public class Main {
    public static void main(String[] args) {
        // Retrieve the lock-free Singleton Manager[cite: 1]
        NotificationManager manager = NotificationManager.getInstance();

        // System Initialization
        manager.registerProduct("IPHONE_15", new IphoneObservableImpl());
        manager.registerProduct("MACBOOK_PRO", new IphoneObservableImpl());

        // Users subscribe via the Manager
        manager.subscribeToProduct("IPHONE_15", new MobileAlertObserverImpl("alice_mobile"));
        manager.subscribeToProduct("IPHONE_15", new MobileAlertObserverImpl("bob_mobile"));

        // A separate worker thread processes an incoming shipment
        new Thread(() -> {
            System.out.println("Warehouse thread processing iPhone shipment...");
            manager.receiveStockShipment("IPHONE_15", 50);
        }).start();
    }
}