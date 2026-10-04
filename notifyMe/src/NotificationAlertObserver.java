//Observer interface (push model)
public interface NotificationAlertObserver {
    // Inject the state directly to decouple from the Observable
    void update(int currentStock);
}